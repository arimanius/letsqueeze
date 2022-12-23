package edu.arimanius.letsqueeze.data.repository

import androidx.lifecycle.LiveData
import edu.arimanius.letsqueeze.data.dao.*
import edu.arimanius.letsqueeze.data.entity.*

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class ProfileRepository(
    private val userDao: UserDao,
) {

    fun getUser(): LiveData<User> {
        return userDao.getLoggedInUser()
    }

    suspend fun save(username: String, displayName: String, phoneNumber: String) {
        userDao.updateProfile(username, displayName, phoneNumber)
    }
}
