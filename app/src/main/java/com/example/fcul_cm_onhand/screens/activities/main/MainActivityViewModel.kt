package com.example.fcul_cm_onhand.screens.activities.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fcul_cm_onhand.Services.AlertDTO
import com.example.fcul_cm_onhand.model.UserType
import com.example.fcul_cm_onhand.repositories.IAlertRepository
import com.google.firebase.firestore.ListenerRegistration
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(): ViewModel() {

    @Inject
    lateinit var repo: IAlertRepository

    private lateinit var subscription: ListenerRegistration

    var userType: UserType? = null

    /* ******************** Receiver Functionalities ******************** */

    fun sendAlert() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.sendAlert(AlertDTO(UUID.randomUUID().toString(), "b", "c", "d"))
        }
    }

    /* ******************** Giver Functionalities ******************** */

    fun startSubToAlerts() {
        subscription = repo.subscribeToAlerts(
            onSubscriptionError = { error(it) },
            onStateChange = {
                Log.v("NEW_ALERT", "id: ${it.id}, giverId: ${it.giverId}, receiverId: ${it.receiverId}, type: ${it.type}")
            }
        )
    }
}