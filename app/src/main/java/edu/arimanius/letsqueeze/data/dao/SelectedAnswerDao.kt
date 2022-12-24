package edu.arimanius.letsqueeze.data.dao

import androidx.room.Dao
import androidx.room.Query
import edu.arimanius.letsqueeze.data.entity.SelectedAnswer

@Dao
interface SelectedAnswerDao: InsertableDao<SelectedAnswer> {
    @Query("SELECT * FROM selected_answers WHERE queezeResultId = :queezeResultId")
    suspend fun getSelectedAnswersByQueezeResultId(queezeResultId: Int): List<SelectedAnswer>
}
