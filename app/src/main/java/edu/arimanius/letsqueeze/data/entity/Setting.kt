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
    ],
)
data class Setting(
    @PrimaryKey
    var username: String,
    var theme: Theme = Theme.SYSTEM,
)

enum class Theme {
    LIGHT,
    DARK,
    SYSTEM
}
