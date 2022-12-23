package edu.arimanius.letsqueeze.data.repository

import edu.arimanius.letsqueeze.data.dao.*
import edu.arimanius.letsqueeze.data.entity.Setting
import edu.arimanius.letsqueeze.data.entity.User
import edu.arimanius.letsqueeze.data.model.LoggedInUser
import org.mindrot.jbcrypt.BCrypt

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class UserRepository(
    private val userDao: UserDao,
    private val settingDao: SettingDao,
) {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
    }

    suspend fun register(username: String, password: String): Result<LoggedInUser> {
        if (userDao.exists(username)) {
            return Result.Error(Exception("User $username already exists"))
        }
        val passwordHash = BCrypt.hashpw(password, BCrypt.gensalt())
        userDao.insert(User(username, passwordHash))
        settingDao.insert(Setting(username))

        setLoggedInUser(LoggedInUser(username))

        return Result.Success(user!!)
    }

    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        // handle login
        val user = userDao.getUserByUsername(username)
            ?: return Result.Error(Exception("User $username not found!"))
        if (!BCrypt.checkpw(password, user.passwordHash)) {
            return Result.Error(Exception("Wrong password!"))
        }
        setLoggedInUser(LoggedInUser(user.username, user.displayName))

        return Result.Success(this.user!!)
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}