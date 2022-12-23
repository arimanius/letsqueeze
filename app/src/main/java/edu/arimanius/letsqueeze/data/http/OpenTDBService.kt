package edu.arimanius.letsqueeze.data.http

import edu.arimanius.letsqueeze.data.http.dto.GetCategoriesResponse
import edu.arimanius.letsqueeze.data.http.dto.GetQuestionsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenTDBService {
    @GET("api.php")
    fun getQuestions(
        @Query("amount") count: Int,
        @Query("category") categoryId: Int,
        @Query("difficulty") difficulty: String,
        @Query("type") type: String = "multiple"
    ): Call<GetQuestionsResponse>

    @GET("api_category.php")
    fun getCategories(): Call<GetCategoriesResponse>
}
