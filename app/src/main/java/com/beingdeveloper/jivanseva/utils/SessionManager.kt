package com.beingdeveloper.jivanseva.utlis

import android.content.Context
import android.media.Session2Token
import com.beingdeveloper.jivanseva.ui.LoginActivity

class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

    fun saveSession(sessionId : String , csrfToken: String){
        prefs.edit().apply{
            putString("SESSION_ID",sessionId)
            putString("CSRF_TOKEN", csrfToken)
            apply()
        }
    }
    fun getSessionId():String? = prefs.getString("SESSION_ID" , null)
    fun getCsrfToken(): String? = prefs.getString("CSRF_TOKEN", null)

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}