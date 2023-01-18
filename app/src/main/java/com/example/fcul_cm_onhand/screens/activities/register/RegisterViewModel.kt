package com.example.fcul_cm_onhand.screens.activities.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fcul_cm_onhand.services.FireDatabaseService
import com.example.fcul_cm_onhand.services.FirebaseAuthService
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val firebaseAuthService: FirebaseAuthService = FirebaseAuthService(),
    private val databaseService: FireDatabaseService = FireDatabaseService()
): ViewModel() {

    private val _registerSuccess = MutableLiveData<Unit>()
    val registerSuccess: LiveData<Unit> = _registerSuccess

    private val _registerError = MutableLiveData<String>()
    val registerError: LiveData<String> = _registerError

    fun register(name: String, email: String, type: String, password: String) {
        viewModelScope.launch {
            try {
                firebaseAuthService.signUp(email, password)

                firebaseAuthService.getUserToken()?.let { databaseService.signUp(name, email, type, it) }

                _registerSuccess.postValue(Unit)
            } catch (e: Exception){
                _registerError.postValue(e.message)
            }
        }
    }
}