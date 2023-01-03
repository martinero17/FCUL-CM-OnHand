package com.example.fcul_cm_onhand.Services

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.tasks.await
import java.sql.Timestamp
import javax.inject.Inject

interface IAlertService {
    suspend fun sendAlert(alert: AlertDTO)
    fun subscribeToAlerts(
        onSubscriptionError: (Exception) -> Unit,
        onStateChange: (alert: AlertDTO) -> Unit
    ): ListenerRegistration
}

data class AlertDTO(
    val id: String,
    val giverId: String,
    val receiverId: String,
    val type: String //TODO(Alterar para um enum com os tipos de alerta (automático e manual))
)

class AlertService @Inject constructor(private val firestore: FirebaseFirestore) : IAlertService {
    override suspend fun sendAlert(alert: AlertDTO) {
        println("")
        firestore.collection("alerts")
            .add(alert)
            .await()
    }

    override fun subscribeToAlerts(
        onSubscriptionError: (Exception) -> Unit,
        onStateChange: (alert: AlertDTO) -> Unit
    ): ListenerRegistration {
        TODO("Not yet implemented")
    }

}