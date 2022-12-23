package edu.arimanius.letsqueeze.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "app_property",
)
data class AppProperty(
    @PrimaryKey
    var key: String,
    var value: String,
)

const val LOGGED_IN_USER_KEY = "logged_in_user"
