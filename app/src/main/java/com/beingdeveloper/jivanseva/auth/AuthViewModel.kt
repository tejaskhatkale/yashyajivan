package com.beingdeveloper.jivanseva.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _loginResponse = MutableLiveData<Response<LoginResponse>>()
    val loginResponse: LiveData<Response<LoginResponse>> = _loginResponse

    fun loginUser(memberId: String, password: String) {
        viewModelScope.launch {
            val response = repository.loginUser(memberId, password)
            _loginResponse.postValue(response)
        }
    }
}
