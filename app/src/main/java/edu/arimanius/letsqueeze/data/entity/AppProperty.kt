package edu.arimanius.letsqueeze.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "app_properties",
)
data class AppProperty(
    @PrimaryKey
    var key: String,
    var value: String,
)

const val LOGGED_IN_USER_KEY = "logged_in_user"
const val ONGOING_QUEEZE_RESULT_ID_KEY = "ongoing_queeze_result_id"
const val ONGOING_SQUEEZE_CURRENT_QUESTION_INDEX_KEY = "ongoing_queeze_current_question_index"
