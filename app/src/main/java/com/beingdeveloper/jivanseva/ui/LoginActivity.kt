package com.beingdeveloper.jivanseva.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.beingdeveloper.jivanseva.MainActivity
import com.beingdeveloper.jivanseva.R
import com.beingdeveloper.jivanseva.data.api.RetrofitClient
import com.beingdeveloper.jivanseva.data.model.LoginRequest
import com.beingdeveloper.jivanseva.utlis.SessionManager
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sessionManager = SessionManager(this)

        findViewById<Button>(R.id.loginButton).setOnClickListener{

            val memberId = findViewById<EditText>(R.id.memberID).text.toString()
            val password = findViewById<EditText>(R.id.password).text.toString()

            if(memberId.isNotEmpty() && password.isNotEmpty()){
                loginUser(memberId , password)
            }else{
                Toast.makeText(this,"Please enter valid credentials" , Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }
    }

    private fun loginUser(memberId :String , password:String){

        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitClient.apiService.login(LoginRequest(memberId,password))

            if(response.isSuccessful){

                val loginResponse = response.body()

                if(loginResponse != null && loginResponse.success){

                    sessionManager.saveSession(loginResponse.sessionId , loginResponse.csrfToken)

                    withContext(Dispatchers.Main){
                        navigateToNextActivity()
                    }
                }else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@LoginActivity,
                            "Login failed: ${loginResponse?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }else{
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@LoginActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun navigateToNextActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
//        finish() // Close LoginActivity
    }
}