package com.example.fcul_cm_onhand.services

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface IAlertService {
    suspend fun sendAlert(alert: AlertDTO)
    fun subscribeToAlerts(
        onSubscriptionError: (Exception) -> Unit,
        onStateChange: (alert: AlertDTO) -> Unit
    ): ListenerRegistration
}

class AlertService @Inject constructor(private val firestore: FirebaseFirestore) : IAlertService {
    override suspend fun sendAlert(alert: AlertDTO) {
        println("")
        firestore
            .collection("alerts")
            .document(alert.giverId)
            .set(alert)
            .await()
    }

    override fun subscribeToAlerts(
        onSubscriptionError: (Exception) -> Unit,
        onStateChange: (alert: AlertDTO) -> Unit
    ): ListenerRegistration {
        return firestore
            .collection("alerts")
            .document("b")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot?.exists() == true) {
                    val alert = snapshot.toAlert()
                    onStateChange(alert)

                    firestore
                        .collection("alerts")
                        .document("b")
                        .delete()
                }
            }
    }

}

data class AlertDTO(
    val id: String,
    val giverId: String,
    val receiverId: String,
    val type: String
)

private fun DocumentSnapshot.toAlert() =
    AlertDTO(
        data?.get("id") as String,
        data?.get("giverId") as String,
        data?.get("receiverId") as String,
        data?.get("type") as String
    )