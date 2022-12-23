package edu.arimanius.letsqueeze.data.dao

import androidx.room.Dao
import edu.arimanius.letsqueeze.data.entity.Category

@Dao
interface CategoryDao: InsertableDao<Category>
