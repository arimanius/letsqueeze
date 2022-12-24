package edu.arimanius.letsqueeze.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import edu.arimanius.letsqueeze.data.entity.LOGGED_IN_USER_KEY
import edu.arimanius.letsqueeze.data.entity.User

@Dao
interface UserDao : InsertableDao<User> {
    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getUserByUsername(username: String): User?

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: Int): User?

    @Query(
        "SELECT user.* " +
                "FROM app_properties prop " +
                "INNER JOIN users user " +
                "ON prop.value = user.id " +
                "WHERE prop.`key` = \"$LOGGED_IN_USER_KEY\""
    )
    fun getLoggedInUser(): LiveData<User>

    @Query(
        "SELECT user.* " +
                "FROM app_properties prop " +
                "INNER JOIN users user " +
                "ON prop.value = user.id " +
                "WHERE prop.`key` = \"$LOGGED_IN_USER_KEY\""
    )
    suspend fun getLoggedInUserAsync(): User

    @Query(
        "SELECT EXISTS(" +
                "SELECT * FROM users " +
                "WHERE username = :username " +
                ")"
    )
    suspend fun exists(username: String): Boolean

    @Query(
        "UPDATE users " +
                "SET username = :username, " +
                "displayName = :displayName, " +
                "phoneNumber = :phoneNumber " +
                "WHERE id = (" +
                "SELECT value FROM app_properties " +
                "WHERE `key` = \"$LOGGED_IN_USER_KEY\"" +
                ")"
    )
    suspend fun updateProfile(username: String, displayName: String, phoneNumber: String)
}
