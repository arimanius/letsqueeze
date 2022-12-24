package edu.arimanius.letsqueeze.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import edu.arimanius.letsqueeze.data.dao.*
import edu.arimanius.letsqueeze.data.entity.*
import edu.arimanius.letsqueeze.data.http.OpenTDBClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.await
import java.util.*

class QueezeRepository(
    private val questionDao: QuestionDao,
    private val answerDao: AnswerDao,
    private val selectedAnswerDao: SelectedAnswerDao,
    private val queezeDao: QueezeDao,
    private val queezeResultDao: QueezeResultDao,
    private val userDao: UserDao,
    private val settingDao: SettingDao,
    private val appPropertyDao: AppPropertyDao,
) {

    fun getQueeze(): LiveData<Pair<Int, List<QuestionWithAnswers>>> {
        val result = MutableLiveData<Pair<Int, List<QuestionWithAnswers>>>()
        CoroutineScope(Dispatchers.Main).launch {
            val setting = settingDao.getAsync()
            val loggedInUser = userDao.getLoggedInUserAsync()
            var queezeId: Int
            val queezeResultId: Int
            val ongoingQueezeResultId = appPropertyDao.get(ONGOING_QUEEZE_RESULT_ID_KEY)
            if (ongoingQueezeResultId != null) {
                queezeId =
                    queezeResultDao.getQueezeResultById(ongoingQueezeResultId.toInt()).queezeId
                queezeResultId = ongoingQueezeResultId.toInt()
            } else {
                try {
                    val response = OpenTDBClient.getClient().getService().getQuestions(
                        setting.numQuestion,
                        setting.categoryId,
                        setting.difficulty.name.lowercase()
                    ).await()

                    queezeId = queezeDao.insert(Queeze(loggedInUser.id)).toInt()

                    response.results.forEach {
                        val question = Question(
                            queezeId,
                            setting.categoryId,
                            Difficulty.fromString(it.difficulty),
                            it.question
                        )
                        val questionId = questionDao.insert(question).toInt()

                        val correctAnswer = Answer(questionId, it.correctAnswer, true)
                        answerDao.insert(correctAnswer)

                        it.incorrectAnswers
                            .map { ans -> Answer(questionId, ans, false) }
                            .forEach { ans -> answerDao.insert(ans) }
                    }
                } catch (e: HttpException) {
                    val queeze = queezeDao.getRandomQueeze(
                        loggedInUser.id,
                        System.currentTimeMillis() - 5 * 24 * 60 * 60 * 1000,
                        System.currentTimeMillis()
                    )
                    queezeId = queeze.id
                }

                queezeResultId = queezeResultDao.insert(QueezeResult(queezeId, Date(), 0)).toInt()
                appPropertyDao.set(
                    AppProperty(
                        ONGOING_QUEEZE_RESULT_ID_KEY,
                        queezeResultId.toString()
                    )
                )
                appPropertyDao.set(AppProperty(ONGOING_SQUEEZE_CURRENT_QUESTION_INDEX_KEY, "0"))
            }
            result.postValue(
                Pair(
                    queezeResultId,
                    questionDao.getQuestionsByQueezeId(queezeId)
                )
            )
        }
        return result
    }

    fun setCurrentQuestionIndex(index: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            appPropertyDao.set(
                AppProperty(
                    ONGOING_SQUEEZE_CURRENT_QUESTION_INDEX_KEY,
                    index.toString()
                )
            )
        }
    }

    fun getCurrentQuestionIndex(): LiveData<String> {
        return appPropertyDao.getLive(ONGOING_SQUEEZE_CURRENT_QUESTION_INDEX_KEY)
    }

    suspend fun selectAnswer(queezeResultId: Int, answerId: Int, score: Int) {
        selectedAnswerDao.insert(SelectedAnswer(queezeResultId, answerId, Date(), score))
    }

    suspend fun updateScores(queezeResultId: Int, score: Int) {
        userDao.updateScore(score)
        queezeResultDao.updateScore(queezeResultId, score)
    }
}