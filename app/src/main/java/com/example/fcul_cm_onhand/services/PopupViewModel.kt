package com.example.fcul_cm_onhand.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fcul_cm_onhand.model.users.User
import kotlinx.coroutines.launch

class PopupViewModel(
    private val firebaseAuthService: FirebaseAuthService = FirebaseAuthService(),
    private val databaseService: FireDatabaseService = FireDatabaseService()
) : ViewModel() {


    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun addUserToGiver(giverEmail: String, receiverEmail: String) {
        viewModelScope.launch {
            databaseService.addUserToGiver(giverEmail, receiverEmail)
        }

    }

    fun getLoggedUser(){
        viewModelScope.launch {
            val result = firebaseAuthService.getUserToken()
                ?.let { databaseService.getUserByEmail("ola@gmail.com") }
            _user.postValue(result!!)
        }
    }
}