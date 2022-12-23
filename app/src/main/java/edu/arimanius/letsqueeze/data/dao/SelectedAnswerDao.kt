package edu.arimanius.letsqueeze.data.dao

import androidx.room.Dao
import edu.arimanius.letsqueeze.data.entity.SelectedAnswer

@Dao
interface SelectedAnswerDao: InsertableDao<SelectedAnswer>