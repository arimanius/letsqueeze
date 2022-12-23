package edu.arimanius.letsqueeze.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "answers",
    foreignKeys = [
        ForeignKey(
            entity = Question::class,
            parentColumns = ["id"],
            childColumns = ["questionId"],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [
        Index(value = ["questionId"])
    ]
)
data class Answer(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var questionId: Int,
    var content: String,
    var correct: Boolean,
)
