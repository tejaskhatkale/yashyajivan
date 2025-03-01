package com.beingdeveloper.jivanseva.auth

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private var prefs: SharedPreferences =
        context.getSharedPreferences("UserSession", Context.MODE_PRIVATE)

    fun saveAuthToken(userId: String?, token: String?) {
        val editor = prefs.edit()
        editor.putString("USER_ID", userId)
        editor.putString("TOKEN", token)
        editor.putBoolean("isLoggedIn", true)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean("isLoggedIn", false)
    }

    fun logoutUser() {
        prefs.edit().clear().apply()
    }
}
