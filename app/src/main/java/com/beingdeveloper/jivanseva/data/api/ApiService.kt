package com.beingdeveloper.jivanseva.data.api

import com.beingdeveloper.jivanseva.data.model.LoginRequest
import com.beingdeveloper.jivanseva.data.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun login(@Body request: LoginRequest):Response<LoginResponse>
}