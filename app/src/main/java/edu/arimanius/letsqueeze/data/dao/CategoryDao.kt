package edu.arimanius.letsqueeze.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.arimanius.letsqueeze.data.entity.Category

@Dao
interface CategoryDao: InsertableDao<Category> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insert(entity: Category): Long

    @Query("SELECT * FROM categories")
    fun getCategories(): LiveData<List<Category>>

    @Query("SELECT * FROM categories WHERE id = :id")
    fun getCategoryById(id: Int): LiveData<Category>
}
