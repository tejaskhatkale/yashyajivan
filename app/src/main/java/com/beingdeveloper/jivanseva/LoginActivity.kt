package com.beingdeveloper.jivanseva

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.beingdeveloper.jivanseva.MainActivity
import com.beingdeveloper.jivanseva.R
import com.beingdeveloper.jivanseva.auth.*

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var loginButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var sessionManager: SessionManager

    private val authViewModel: AuthViewModel by lazy {
        ViewModelProvider(this, AuthViewModelFactory(AuthRepository(AuthApiService.create())))[AuthViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        loginButton = findViewById(R.id.loginbutton)
        progressBar = findViewById(R.id.progressBar)

        sessionManager = SessionManager(this)

        loginButton.setOnClickListener {
            val memberId = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (memberId.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter Member ID and Password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            loginUser(memberId, password)
        }
    }

    private fun loginUser(memberId: String, password: String) {
        progressBar.visibility = View.VISIBLE
        authViewModel.loginUser(memberId, password)

        authViewModel.loginResponse.observe(this) { response ->
            progressBar.visibility = View.GONE
            if (response.isSuccessful && response.body() != null) {
                val user = response.body()!!
                sessionManager.saveAuthToken(user.userId, user.token)

                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Login Failed. Please check your credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
