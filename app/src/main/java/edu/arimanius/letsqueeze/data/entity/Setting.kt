package edu.arimanius.letsqueeze.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Locale

@Entity(
    tableName = "settings",
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class Setting(
    var theme: Theme = Theme.SYSTEM,
    var difficulty: Difficulty = Difficulty.MEDIUM,
    var numQuestion: Int = 10,
    var categoryId: Int = 9,
) {
    @PrimaryKey
    var id: Int = 0
}

enum class Theme {
    LIGHT,
    DARK,
    SYSTEM;

    private fun getRepresentation(): String {
        return this.name.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }
    }

    override fun toString(): String {
        return this.getRepresentation()
    }
}
