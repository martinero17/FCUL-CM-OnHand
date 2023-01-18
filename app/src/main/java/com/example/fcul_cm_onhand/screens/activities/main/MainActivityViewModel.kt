package com.example.fcul_cm_onhand.screens.activities.main

import android.app.Application
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fcul_cm_onhand.R
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
class MainActivityViewModel @Inject constructor(val application: Application): ViewModel() {

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

                //Todo: Mudar estas strings
                val builder = NotificationCompat.Builder(application.applicationContext, application.getString(R.string.alert_channel_id))
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle("ALGUÉM ESTÁ A MORRER")
                    .setContentText("O care receiver ${it.receiverId} está a morrer")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)

                with(NotificationManagerCompat.from(application.applicationContext)) {
                    notify(Random().nextInt(), builder.build())
                }
            }
        )
    }
}