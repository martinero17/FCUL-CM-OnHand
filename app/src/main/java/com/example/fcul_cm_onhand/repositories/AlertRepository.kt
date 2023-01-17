package com.example.fcul_cm_onhand.repositories

import com.example.fcul_cm_onhand.Services.AlertDTO
import com.example.fcul_cm_onhand.Services.IAlertService
import com.google.firebase.firestore.ListenerRegistration
import javax.inject.Inject

interface IAlertRepository {
    suspend fun sendAlert(alert: AlertDTO)
    fun subscribeToAlerts(
        onSubscriptionError: (Exception) -> Unit,
        onStateChange: (path: AlertDTO) -> Unit
    ): ListenerRegistration
}

class AlertRepository @Inject constructor(private val service: IAlertService): IAlertRepository  {
    override suspend fun sendAlert(alert: AlertDTO) {
        service.sendAlert(alert)
    }

    override fun subscribeToAlerts(
        onSubscriptionError: (Exception) -> Unit,
        onStateChange: (path: AlertDTO) -> Unit
    ): ListenerRegistration {
        return service.subscribeToAlerts(onSubscriptionError, onStateChange)
    }
}