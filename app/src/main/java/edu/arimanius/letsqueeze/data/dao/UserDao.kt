package edu.arimanius.letsqueeze.data.dao

import androidx.room.Dao
import androidx.room.Query
import edu.arimanius.letsqueeze.data.entity.User

@Dao
interface UserDao : InsertableDao<User> {
    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getUserByUsername(username: String): User?

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: Int): User?

    @Query(
        "SELECT EXISTS(" +
                "SELECT * FROM users " +
                "WHERE username = :username " +
                ")"
    )
    suspend fun exists(username: String): Boolean
}
