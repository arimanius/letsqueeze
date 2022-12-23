package edu.arimanius.letsqueeze.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
    indices = [
        Index(value = ["username"], unique = true),
    ],
)
data class User(
    var username: String,
    var passwordHash: String,
    var displayName: String? = null,
    var phoneNumber: String = "",
    var score: Int = 0,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0;
}
