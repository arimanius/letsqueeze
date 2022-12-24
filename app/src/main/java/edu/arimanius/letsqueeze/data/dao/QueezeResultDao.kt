package edu.arimanius.letsqueeze.data.dao

import androidx.room.Dao
import androidx.room.Query
import edu.arimanius.letsqueeze.data.entity.LOGGED_IN_USER_KEY
import edu.arimanius.letsqueeze.data.entity.QueezeResult

@Dao
interface QueezeResultDao: InsertableDao<QueezeResult> {
    @Query(
        "UPDATE queeze_results " +
                "SET score = queeze_results.score + :change " +
                "WHERE id = :id"
    )
    suspend fun updateScore(change: Int, id: Int)
}
