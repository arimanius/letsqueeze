package edu.arimanius.letsqueeze.ui.scoreboard

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.arimanius.letsqueeze.data.LetsQueezeDatabase
import edu.arimanius.letsqueeze.data.repository.ScoreboardRepository

class ScoreboardViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScoreboardViewModel::class.java)) {
            return ScoreboardViewModel(
                scoreboardRepository = ScoreboardRepository(
                    LetsQueezeDatabase.getInstance(context).userDao(),
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
