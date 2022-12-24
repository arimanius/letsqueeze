package edu.arimanius.letsqueeze.data.repository

import edu.arimanius.letsqueeze.data.dao.*
import edu.arimanius.letsqueeze.data.entity.ONGOING_QUEEZE_RESULT_ID_KEY
import edu.arimanius.letsqueeze.data.entity.SelectedAnswer

class QueezeResultRepository(
    private val questionDao: QuestionDao,
    private val selectedAnswerDao: SelectedAnswerDao,
    private val queezeResultDao: QueezeResultDao,
    private val appPropertyDao: AppPropertyDao,
) {
    suspend fun getOngoingQuestionsWithAnswers(): List<QuestionWithAnswers> {
        val queezeResultId = appPropertyDao.get(ONGOING_QUEEZE_RESULT_ID_KEY)!!.toInt()
        val questionResult = queezeResultDao.getQueezeResultById(queezeResultId)
        return questionDao.getQuestionsByQueezeId(questionResult.queezeId)
    }

    suspend fun getOngoingSelectedAnswers(): List<SelectedAnswer> {
        val queezeResultId = appPropertyDao.get(ONGOING_QUEEZE_RESULT_ID_KEY)!!.toInt()
        return selectedAnswerDao.getSelectedAnswersByQueezeResultId(queezeResultId)
    }

    suspend fun unsetOngoingQueezeId() {
        appPropertyDao.unset(ONGOING_QUEEZE_RESULT_ID_KEY)
    }
}
