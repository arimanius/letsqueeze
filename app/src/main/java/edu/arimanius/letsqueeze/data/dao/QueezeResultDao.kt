package edu.arimanius.letsqueeze.data.dao

import androidx.room.Dao
import androidx.room.Query
import edu.arimanius.letsqueeze.data.entity.QueezeResult

@Dao
interface QueezeResultDao: InsertableDao<QueezeResult> {
    @Query(
        "UPDATE queeze_results " +
                "SET score = queeze_results.score + :change " +
                "WHERE id = :id"
    )
    suspend fun updateScore(id: Int, change: Int)

    @Query("SELECT * FROM queeze_results WHERE id = :id")
    suspend fun getQueezeResultById(id: Int): QueezeResult

    @Query("SELECT id FROM queeze_results " +
            "ORDER BY score DESC LIMIT 1")
    suspend fun getBestResultId(): Int
}
