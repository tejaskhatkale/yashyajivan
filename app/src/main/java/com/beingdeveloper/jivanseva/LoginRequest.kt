package com.beingdeveloper.jivanseva.auth

data class LoginRequest(
    val `member-id`: String,
    val password: String
)
