package edu.arimanius.letsqueeze.ui.queeze

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import edu.arimanius.letsqueeze.data.repository.QueezeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QueezeViewModel(private val queezeRepository: QueezeRepository) : ViewModel() {
    val queeze = queezeRepository.getQueeze()
    var currentQuestionIndex: LiveData<String> = queezeRepository.getCurrentQuestionIndex()

    fun setCurrentQuestionIndex(value: Int) {
        queezeRepository.setCurrentQuestionIndex(value)
    }

    fun submitAnswer(queezeResultId: Int, answerId: Int, score: Int) {
        if (answerId == -1) return
        CoroutineScope(Dispatchers.IO).launch {
            queezeRepository.selectAnswer(queezeResultId, answerId, score)
            queezeRepository.updateScores(queezeResultId, score)
        }
    }
}
