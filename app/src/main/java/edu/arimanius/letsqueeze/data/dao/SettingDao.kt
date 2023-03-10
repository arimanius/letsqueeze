package edu.arimanius.letsqueeze.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.arimanius.letsqueeze.data.entity.Setting

@Dao
interface SettingDao : InsertableDao<Setting> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insert(entity: Setting): Long

    @Query("SELECT * FROM settings")
    fun get(): LiveData<Setting>

    @Query("SELECT * FROM settings")
    suspend fun getAsync(): Setting

    @Query("SELECT EXISTS(SELECT * FROM settings)")
    suspend fun exists(): Boolean
}
