package edu.arimanius.letsqueeze.data.dao

import androidx.room.Dao
import edu.arimanius.letsqueeze.data.entity.Queeze

@Dao
interface QueezeDao: InsertableDao<Queeze>
