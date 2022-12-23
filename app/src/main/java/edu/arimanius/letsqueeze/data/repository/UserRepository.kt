package edu.arimanius.letsqueeze.data.repository

import edu.arimanius.letsqueeze.data.dao.*
import edu.arimanius.letsqueeze.data.entity.AppProperty
import edu.arimanius.letsqueeze.data.entity.LOGGED_IN_USER_KEY
import edu.arimanius.letsqueeze.data.entity.User
import edu.arimanius.letsqueeze.data.model.LoggedInUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class UserRepository(
    private val appPropertyDao: AppPropertyDao,
    private val userDao: UserDao,
) {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        CoroutineScope(Dispatchers.IO).launch {
            user = appPropertyDao
                .get(LOGGED_IN_USER_KEY)
                ?.let { userDao.getUserById(it.toInt()) }
                ?.let { LoggedInUser(it.id, it.username, it.displayName) }
        }
    }

    suspend fun logout() {
        user = null
        appPropertyDao.unset(LOGGED_IN_USER_KEY)
    }

    suspend fun register(username: String, password: String): Result<LoggedInUser> {
        if (userDao.exists(username)) {
            return Result.Error(Exception("User $username already exists"))
        }
        val passwordHash = BCrypt.hashpw(password, BCrypt.gensalt())
        val id = userDao.insert(User(username, passwordHash))

        setLoggedInUser(LoggedInUser(id.toInt(), username))

        return Result.Success(user!!)
    }

    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        // handle login
        val user = userDao.getUserByUsername(username)
            ?: return Result.Error(Exception("User $username not found!"))
        if (!BCrypt.checkpw(password, user.passwordHash)) {
            return Result.Error(Exception("Wrong password!"))
        }
        setLoggedInUser(LoggedInUser(user.id, user.username, user.displayName))

        return Result.Success(this.user!!)
    }

    private suspend fun setLoggedInUser(loggedInUser: LoggedInUser) {
        user = loggedInUser
        appPropertyDao.set(AppProperty(LOGGED_IN_USER_KEY, loggedInUser.userId.toString()))
    }
}
