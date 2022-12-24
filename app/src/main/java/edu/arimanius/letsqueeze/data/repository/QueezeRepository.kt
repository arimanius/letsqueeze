package edu.arimanius.letsqueeze.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import edu.arimanius.letsqueeze.data.dao.*
import edu.arimanius.letsqueeze.data.entity.Answer
import edu.arimanius.letsqueeze.data.entity.Difficulty
import edu.arimanius.letsqueeze.data.entity.Queeze
import edu.arimanius.letsqueeze.data.entity.Question
import edu.arimanius.letsqueeze.data.http.OpenTDBClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.await

class QueezeRepository(
    private val questionDao: QuestionDao,
    private val answerDao: AnswerDao,
    private val queezeDao: QueezeDao,
    private val userDao: UserDao,
    private val settingDao: SettingDao,
    private val appPropertyDao: AppPropertyDao,
) {

    fun getQueeze(): LiveData<List<QuestionWithAnswers>> {
        val result = MutableLiveData<List<QuestionWithAnswers>>()
        CoroutineScope(Dispatchers.Main).launch {
            val setting = settingDao.getAsync()
            val response = OpenTDBClient.getClient().getService().getQuestions(
                setting.numQuestion,
                setting.categoryId,
                setting.difficulty.name.lowercase()
            ).await()

            val loggedInUser = userDao.getLoggedInUserAsync()
            val queezeId = queezeDao.insert(Queeze(loggedInUser.id))

            response.results.forEach {
                val question = Question(
                    queezeId.toInt(),
                    setting.categoryId,
                    Difficulty.fromString(it.difficulty),
                    it.question
                )
                val questionId = questionDao.insert(question)

                val correctAnswer = Answer(questionId.toInt(), it.correctAnswer, true)
                answerDao.insert(correctAnswer)

                it.incorrectAnswers
                    .map { ans -> Answer(questionId.toInt(), ans, false) }
                    .forEach { ans -> answerDao.insert(ans) }
            }

            result.postValue(questionDao.getQuestionsByQueezeId(queezeId.toInt()))
        }
        return result
    }
}