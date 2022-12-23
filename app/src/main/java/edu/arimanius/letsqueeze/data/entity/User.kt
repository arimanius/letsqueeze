package edu.arimanius.letsqueeze.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
)
data class User(
    @PrimaryKey
    var username: String,
    var passwordHash: String,
    var displayName: String = "",
    var phoneNumber: String = "",
    var score: Int = 0,
)
