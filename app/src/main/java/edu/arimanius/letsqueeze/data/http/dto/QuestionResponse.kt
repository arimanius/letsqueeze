package edu.arimanius.letsqueeze.data.http.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class QuestionResponse(
    @JsonProperty("category")
    val category: String = "",
    @JsonProperty("type")
    val type: String = "",
    @JsonProperty("difficulty")
    val difficulty: String = "",
    @JsonProperty("question")
    val question: String = "",
    @JsonProperty("correct_answer")
    val correctAnswer: String = "",
    @JsonProperty("incorrect_answers")
    val incorrectAnswers: List<String>,
)
