package edu.arimanius.letsqueeze.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import edu.arimanius.letsqueeze.data.dao.*
import edu.arimanius.letsqueeze.data.entity.*
import edu.arimanius.letsqueeze.data.http.OpenTDBClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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
            val response = OpenTDBClient.getClient().getService().getQuestions(
                setting.numQuestion,
                setting.categoryId,
                setting.difficulty.name.lowercase()
            ).await()

            val loggedInUser = userDao.getLoggedInUserAsync()
            val queezeId = queezeDao.insert(Queeze(loggedInUser.id)).toInt()

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

            val queezeResultId =
                queezeResultDao.insert(QueezeResult(queezeId, Date(), 0)).toInt()

            result.postValue(
                Pair(
                    queezeResultId,
                    questionDao.getQuestionsByQueezeId(queezeId)
                )
            )
        }
        return result
    }

    suspend fun selectAnswer(queezeResultId: Int, answerId: Int, score: Int) {
        selectedAnswerDao.insert(SelectedAnswer(queezeResultId, answerId, Date(), score))
    }

    suspend fun updateScores(queezeResultId: Int, score: Int) {
        userDao.updateScore(score)
        queezeResultDao.updateScore(score, score)
    }
}