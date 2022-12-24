package edu.arimanius.letsqueeze.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "selected_answers",
    foreignKeys = [
        ForeignKey(
            entity = QueezeResult::class,
            parentColumns = ["id"],
            childColumns = ["queezeResultId"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = Answer::class,
            parentColumns = ["id"],
            childColumns = ["answerId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index(value = ["queezeResultId"]),
        Index(value = ["answerId"]),
    ],
)
data class SelectedAnswer(
    var queezeResultId: Int,
    var answerId: Int,
    var selectedAt: Date,
    var score: Int,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
