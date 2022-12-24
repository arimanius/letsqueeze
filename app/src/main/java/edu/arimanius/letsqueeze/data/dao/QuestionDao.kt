package edu.arimanius.letsqueeze.data.dao

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Relation
import edu.arimanius.letsqueeze.data.entity.Answer
import edu.arimanius.letsqueeze.data.entity.Question

@Dao
interface QuestionDao : InsertableDao<Question>

data class QuestionWithAnswers(
    @Embedded val question: Question,
    @Relation(
        parentColumn = "id",
        entityColumn = "questionId"
    ) val answers: List<Answer>
)
