package edu.arimanius.letsqueeze.data.dao

import androidx.room.Dao
import edu.arimanius.letsqueeze.data.entity.Question

@Dao
interface QuestionDao: InsertableDao<Question>
