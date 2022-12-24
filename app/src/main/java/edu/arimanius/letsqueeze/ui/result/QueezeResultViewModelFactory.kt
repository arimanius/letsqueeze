package edu.arimanius.letsqueeze.ui.result

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.arimanius.letsqueeze.data.LetsQueezeDatabase
import edu.arimanius.letsqueeze.data.repository.QueezeResultRepository

class QueezeResultViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QueezeResultViewModel::class.java)) {
            return QueezeResultViewModel(
                queezeResultRepository = QueezeResultRepository(
                    LetsQueezeDatabase.getInstance(context).questionDao(),
                    LetsQueezeDatabase.getInstance(context).selectedAnswerDao(),
                    LetsQueezeDatabase.getInstance(context).queezeResultDao(),
                    LetsQueezeDatabase.getInstance(context).appPropertyDao(),
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
