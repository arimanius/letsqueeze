package edu.arimanius.letsqueeze.data.dao

import androidx.room.Dao
import androidx.room.Query
import edu.arimanius.letsqueeze.data.entity.Queeze

@Dao
interface QueezeDao: InsertableDao<Queeze> {
    @Query("SELECT * FROM queezes WHERE userId = :userId " +
            "AND createdAt BETWEEN :from AND :to " +
            "ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomQueeze(userId: Int, from: Long, to: Long): Queeze
}
