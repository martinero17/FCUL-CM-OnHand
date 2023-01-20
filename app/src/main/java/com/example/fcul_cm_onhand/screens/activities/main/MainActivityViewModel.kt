package com.example.fcul_cm_onhand.screens.activities.main

import android.app.Application
import android.location.Location
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fcul_cm_onhand.R
import com.example.fcul_cm_onhand.services.AlertDTO
import com.example.fcul_cm_onhand.model.UserType
import com.example.fcul_cm_onhand.repositories.IAlertRepository
import com.example.fcul_cm_onhand.repositories.ICheckInRepository
import com.example.fcul_cm_onhand.repositories.ILocationRepository
import com.example.fcul_cm_onhand.services.CheckInDTO
import com.example.fcul_cm_onhand.services.LocationDTO
import com.google.firebase.firestore.ListenerRegistration
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(val application: Application): ViewModel() {

    @Inject
    lateinit var alertRepo: IAlertRepository
    @Inject
    lateinit var checkInRepo: ICheckInRepository
    @Inject
    lateinit var locationRepo: ILocationRepository

    private lateinit var alertSubscription: ListenerRegistration
    private lateinit var checkInSubscription: ListenerRegistration

    private val _location = MutableLiveData<LocationDTO>()
    var location: LiveData<LocationDTO> = _location

    var userType: UserType? = null

    /* ******************** Receiver Functionalities ******************** */

    fun sendAlert(alertType: String) {
        viewModelScope.launch(Dispatchers.IO) {
            alertRepo.sendAlert(AlertDTO(UUID.randomUUID().toString(), "b", "c", alertType))
        }
    }

    fun sendCheckIn() {
        viewModelScope.launch(Dispatchers.IO) {
            checkInRepo.sendCheckIn(CheckInDTO(UUID.randomUUID().toString(), "b", "c"))
        }
    }

    fun sendLocation(location: Location) {
        viewModelScope.launch(Dispatchers.IO) {
            locationRepo.sendLocation(location)
        }
    }

    /* ******************** Giver Functionalities ******************** */

    fun startSubToAlerts() {
        alertSubscription = alertRepo.subscribeToAlerts(
            onSubscriptionError = { error(it) },
            onStateChange = {

                //Todo: Mudar estas strings
                val builder = NotificationCompat.Builder(application.applicationContext, application.getString(R.string.alert_channel_id))
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle("Alert received!")
                    .setContentText("The care receiver ${it.receiverId} has sent an ${it.type} alert")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)

                with(NotificationManagerCompat.from(application.applicationContext)) {
                    notify(Random().nextInt(), builder.build())
                }
            }
        )
    }

    fun startSubToCheckIn() {
        checkInSubscription = checkInRepo.subscribeToAlerts(
            onSubscriptionError = { error(it) },
            onStateChange = {

                val builder = NotificationCompat.Builder(application.applicationContext, "CHECK_IN_CHANNEL")
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle("CheckIn received!")
                    .setContentText("The care receiver ${it.receiverId} has checked in")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                with(NotificationManagerCompat.from(application.applicationContext)) {
                    notify(Random().nextInt(), builder.build())
                }
            }
        )
    }

    fun startSubToLocation() {
        checkInSubscription = locationRepo.subscribeToLocation(
            onSubscriptionError = { error(it) },
            onStateChange = {
                val builder = NotificationCompat.Builder(application.applicationContext, "LOCATION_CHANNEL")
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle("Location Received!")
                    .setContentText("A care receiver has shared their location with you")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                with(NotificationManagerCompat.from(application.applicationContext)) {
                    notify(Random().nextInt(), builder.build())
                }
            }
        )
    }

    fun downloadLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            _location.postValue(locationRepo.downloadLocation())
        }
    }
}