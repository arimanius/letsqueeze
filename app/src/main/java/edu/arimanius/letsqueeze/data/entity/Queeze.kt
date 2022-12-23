package edu.arimanius.letsqueeze.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "queezes",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["username"],
            childColumns = ["username"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index(value = ["username"])
    ]
)
data class Queeze(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var username: String,
)
