package edu.arimanius.letsqueeze.ui.queeze

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.arimanius.letsqueeze.data.LetsQueezeDatabase
import edu.arimanius.letsqueeze.data.repository.QueezeRepository

class QueezeViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QueezeViewModel::class.java)) {
            return QueezeViewModel(
                queezeRepository = QueezeRepository(
                    LetsQueezeDatabase.getInstance(context).questionDao(),
                    LetsQueezeDatabase.getInstance(context).answerDao(),
                    LetsQueezeDatabase.getInstance(context).queezeDao(),
                    LetsQueezeDatabase.getInstance(context).userDao(),
                    LetsQueezeDatabase.getInstance(context).settingDao(),
                    LetsQueezeDatabase.getInstance(context).appPropertyDao(),
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
