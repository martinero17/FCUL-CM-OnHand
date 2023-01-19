package com.example.fcul_cm_onhand.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fcul_cm_onhand.model.users.User
import com.example.fcul_cm_onhand.model.users.UserGiver
import com.example.fcul_cm_onhand.model.users.UserReceiver
import kotlinx.coroutines.launch

class PopupViewModel(
    private val firebaseAuthService: FirebaseAuthService = FirebaseAuthService(),
    private val databaseService: FireDatabaseService = FireDatabaseService()
) : ViewModel() {


    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _receivers = MutableLiveData(mutableListOf<UserReceiver>())
    val receivers: LiveData<MutableList<UserReceiver>> = _receivers

    fun addUserToGiver(giverEmail: String, receiverEmail: String) {
        viewModelScope.launch {
            databaseService.addUserToGiver(giverEmail, receiverEmail)
        }

    }

    fun getLoggedUser(){
        viewModelScope.launch {
            val result = firebaseAuthService.getUserEmail()
                ?.let { databaseService.getUserByEmail(it) }
            _user.postValue(result!!)
        }
    }

    fun getGiverUsers() {
        viewModelScope.launch {
            val result = firebaseAuthService.getUserEmail()
                ?.let { databaseService.getUserGiverByEmail(it) }
            val giver = result as UserGiver
            _receivers.postValue(giver.receivers)
        }
    }
}