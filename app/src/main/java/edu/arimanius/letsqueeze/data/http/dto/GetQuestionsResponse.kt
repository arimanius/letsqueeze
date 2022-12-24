package edu.arimanius.letsqueeze.data.http.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class GetQuestionsResponse(
    @JsonProperty("response_code")
    val responseCode: Int = 0,
    @JsonProperty("results")
    val results: List<QuestionResponse>
)
