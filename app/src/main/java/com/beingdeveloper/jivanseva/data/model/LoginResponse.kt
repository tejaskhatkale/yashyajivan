package com.beingdeveloper.jivanseva.data.model

data class LoginResponse (
    val sessionId : String,
    val csrfToken :String,
    val success : Boolean,
    val message: String
)
