package edu.arimanius.letsqueeze.data.dao

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import edu.arimanius.letsqueeze.data.entity.Answer
import edu.arimanius.letsqueeze.data.entity.Question

@Dao
interface QuestionDao : InsertableDao<Question>{
    @Transaction
    @Query("SELECT * FROM questions WHERE queezeId = :queezeId")
    suspend fun getQuestionsByQueezeId(queezeId : Int): List<QuestionWithAnswers>
}

data class QuestionWithAnswers(
    @Embedded val question: Question,
    @Relation(
        parentColumn = "id",
        entityColumn = "questionId"
    ) val answers: List<Answer>
)
