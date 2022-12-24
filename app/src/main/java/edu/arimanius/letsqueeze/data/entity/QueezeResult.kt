package edu.arimanius.letsqueeze.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "queeze_results",
)
data class QueezeResult(
    var queezeId: Int,
    var takenAt: Date,
    var score: Int,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
