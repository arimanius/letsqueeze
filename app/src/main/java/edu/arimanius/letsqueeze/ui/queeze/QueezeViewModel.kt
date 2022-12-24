package edu.arimanius.letsqueeze.ui.queeze

import androidx.lifecycle.ViewModel
import edu.arimanius.letsqueeze.data.repository.QueezeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QueezeViewModel(private val queezeRepository: QueezeRepository): ViewModel() {
    val queeze = queezeRepository.getQueeze()
    var currentQuestionIndex = 0

    fun submitAnswer(queezeResultId: Int, answerId: Int, score: Int) {
        if (answerId == -1) return
        CoroutineScope(Dispatchers.IO).launch {
            while (queezeResultId == 0){}

            queezeRepository.selectAnswer(queezeResultId, answerId, score)
            queezeRepository.updateScores(queezeResultId, score)
        }
    }
}