package edu.arimanius.letsqueeze.data.http.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class QuestionResponse(
    val category: String,
    val type: String,
    val difficulty: String,
    val question: String,
    @JsonProperty("correct_answer")
    val correctAnswer: String,
    @JsonProperty("incorrect_answers")
    val incorrectAnswers: List<String>,
)
