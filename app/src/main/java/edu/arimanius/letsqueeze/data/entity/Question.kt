package edu.arimanius.letsqueeze.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Locale

@Entity(
    tableName = "questions",
    foreignKeys = [
        ForeignKey(
            entity = Queeze::class,
            parentColumns = ["id"],
            childColumns = ["queezeId"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index(value = ["queezeId"]),
        Index(value = ["categoryId"]),
    ]
)
data class Question(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var queezeId: Int,
    var categoryId: Int,
    var difficulty: Difficulty,
    var content: String,
)

enum class Difficulty {
    EASY,
    MEDIUM,
    HARD;

    companion object {
        fun fromString(lowerCaseName: String): Difficulty {
            return enumValueOf(lowerCaseName.uppercase())
        }
    }

    fun getRepresentation(): String {
        return this.name.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }
    }
}
