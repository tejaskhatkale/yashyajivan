package com.beingdeveloper.jivanseva.auth

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("login")
    suspend fun loginUser(@Body request: LoginRequest): Response<LoginResponse>

    companion object {
        private const val BASE_URL = "https://jivanseva.com/app/api/"

        fun create(): AuthApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(AuthApiService::class.java)
        }
    }
}
