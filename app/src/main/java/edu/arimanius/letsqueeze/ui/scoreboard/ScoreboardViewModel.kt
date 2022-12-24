package edu.arimanius.letsqueeze.ui.scoreboard

import androidx.lifecycle.ViewModel
import edu.arimanius.letsqueeze.data.repository.ScoreboardRepository

class ScoreboardViewModel(scoreboardRepository: ScoreboardRepository) : ViewModel() {
    val users = scoreboardRepository.getUsers()
    val loggedInUser = scoreboardRepository.getLoggedInUser()
}
