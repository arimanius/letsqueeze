package edu.arimanius.letsqueeze.data.http.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class GetCategoriesResponse(
    @JsonProperty("trivia_categories")
    val categories: List<CategoryResponse>
)
