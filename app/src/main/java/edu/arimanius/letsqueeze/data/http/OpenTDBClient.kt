package edu.arimanius.letsqueeze.data.http

import retrofit2.Retrofit

class OpenTDBClient private constructor(private val retrofit: Retrofit) {

    companion object {
        private var instance: OpenTDBClient? = null
        fun getClient(): OpenTDBClient {
            return instance?: synchronized(this) {
                instance ?: OpenTDBClient(
                    Retrofit.Builder()
                        .baseUrl("https://opentdb.com/")
                        .build()
                ).also { instance = it }
            }
        }
    }

    fun getService() =
        retrofit.create(OpenTDBService::class.java)
}
