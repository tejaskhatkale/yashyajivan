package com.beingdeveloper.jivanseva.auth

import retrofit2.Response

class AuthRepository(private val apiService: AuthApiService) {

    suspend fun loginUser(memberId: String, password: String): Response<LoginResponse> {
        return apiService.loginUser(LoginRequest(memberId, password))
    }
}
