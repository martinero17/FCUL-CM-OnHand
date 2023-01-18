package com.example.fcul_cm_onhand.screens.activities.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fcul_cm_onhand.services.FirebaseAuthService
import kotlinx.coroutines.launch

class LoginViewModel(private val firebaseAuthService: FirebaseAuthService = FirebaseAuthService()): ViewModel() {
    private val _loginSuccess = MutableLiveData<Unit>()
    val loginSuccess: LiveData<Unit> =_loginSuccess

    private val _loginError = MutableLiveData<String>()
    val loginError: LiveData<String> =_loginError

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                firebaseAuthService.signIn(email, password)
                _loginSuccess.postValue(Unit)
            } catch (e: Exception){
                _loginError.postValue(e.message)
            }
        }
    }
}