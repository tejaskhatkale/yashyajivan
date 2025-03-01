package com.beingdeveloper.jivanseva.auth

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val userId: String?,
    val token: String?
)
