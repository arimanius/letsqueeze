package edu.arimanius.letsqueeze.data.repository

import androidx.lifecycle.LiveData
import edu.arimanius.letsqueeze.data.dao.UserDao
import edu.arimanius.letsqueeze.data.entity.User

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class ScoreboardRepository(
    private val userDao: UserDao,
) {

    fun getUsers(): LiveData<List<User>> {
        return userDao.getUsers()
    }

    fun getLoggedInUser(): LiveData<User> {
        return userDao.getLoggedInUser()
    }
}
