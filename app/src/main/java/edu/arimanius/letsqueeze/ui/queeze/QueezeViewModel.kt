package edu.arimanius.letsqueeze.ui.queeze

import androidx.lifecycle.ViewModel
import edu.arimanius.letsqueeze.data.repository.QueezeRepository

class QueezeViewModel(private val queezeRepository: QueezeRepository): ViewModel() {
    val queeze = queezeRepository.getQueeze()
}