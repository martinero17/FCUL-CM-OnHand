package com.example.fcul_cm_onhand.screens.activities.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fcul_cm_onhand.model.User
import com.example.fcul_cm_onhand.services.FireDatabaseService
import com.example.fcul_cm_onhand.services.FirebaseAuthService
import kotlinx.coroutines.launch

class LoginViewModel(
    private val firebaseAuthService: FirebaseAuthService = FirebaseAuthService(),
    private val databaseService: FireDatabaseService = FireDatabaseService()
): ViewModel() {
    private val _loginSuccess = MutableLiveData<Unit>()
    val loginSuccess: LiveData<Unit> =_loginSuccess

    private val _loginError = MutableLiveData<String>()
    val loginError: LiveData<String> = _loginError

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user


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

    fun getLoggedUser(email: String){
        viewModelScope.launch {
            val result = databaseService.getUserByEmail(email)
            _user.postValue(result!!)
        }
    }

}