package edu.arimanius.letsqueeze.ui.result

import androidx.lifecycle.ViewModel
import edu.arimanius.letsqueeze.data.repository.QueezeResultRepository

class QueezeResultViewModel(private val queezeResultRepository: QueezeResultRepository) : ViewModel() {
    suspend fun getOngoingQuestionsWithAnswers() =
        queezeResultRepository.getOngoingQuestionsWithAnswers()

    suspend fun getOngoingSelectedAnswers() =
        queezeResultRepository.getOngoingSelectedAnswers()

    suspend fun unsetOngoingQueezeId() = queezeResultRepository.unsetOngoingQueezeId()

    suspend fun getOngoingQueezeResult() = queezeResultRepository.getOngoingQueezeResult()

    suspend fun isOngoingTheBest() = queezeResultRepository.isOngoingTheBest()
}
