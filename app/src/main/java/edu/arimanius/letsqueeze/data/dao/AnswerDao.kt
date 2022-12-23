package edu.arimanius.letsqueeze.data.dao

import androidx.room.Dao
import edu.arimanius.letsqueeze.data.entity.Answer

@Dao
interface AnswerDao: InsertableDao<Answer>
