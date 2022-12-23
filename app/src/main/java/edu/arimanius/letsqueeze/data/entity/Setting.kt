package edu.arimanius.letsqueeze.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "settings",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["username"],
            childColumns = ["username"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.SET_NULL,
        )
    ],
)
data class Setting(
    @PrimaryKey
    var username: String,
    var theme: Theme = Theme.SYSTEM,
    var difficulty: Difficulty = Difficulty.MEDIUM,
    var numQuestion: Int = 10,
    var categoryId: Int = 9,
)

enum class Theme {
    LIGHT,
    DARK,
    SYSTEM
}
