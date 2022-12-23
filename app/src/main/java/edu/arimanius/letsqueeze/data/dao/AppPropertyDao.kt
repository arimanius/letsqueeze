package edu.arimanius.letsqueeze.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.arimanius.letsqueeze.data.entity.AppProperty

@Dao
interface AppPropertyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun set(property: AppProperty)

    @Query("SELECT value FROM app_properties WHERE `key` = :key")
    suspend fun get(key: String): String?

    @Query("DELETE FROM app_properties WHERE `key` = :key")
    suspend fun unset(key: String)
}
