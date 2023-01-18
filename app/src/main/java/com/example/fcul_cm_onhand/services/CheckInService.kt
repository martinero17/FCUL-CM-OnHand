package com.example.fcul_cm_onhand.services

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface ICheckInService {
    suspend fun sendCheckIn(checkIn: CheckInDTO)
    fun subscribeToCheckIn(
        onSubscriptionError: (Exception) -> Unit,
        onStateChange: (checkIn: CheckInDTO) -> Unit
    ): ListenerRegistration
}

class CheckInService @Inject constructor(private val firestore: FirebaseFirestore): ICheckInService {
    override suspend fun sendCheckIn(checkIn: CheckInDTO) {
        firestore
            .collection("checkins")
            .document(checkIn.giverId)
            .set(checkIn)
            .await()
    }

    override fun subscribeToCheckIn(
        onSubscriptionError: (Exception) -> Unit,
        onStateChange: (checkIn: CheckInDTO) -> Unit
    ): ListenerRegistration {
        return firestore
            .collection("checkins")
            .document("b")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot?.exists() == true) {
                    val checkIn = snapshot.toCheckIn()
                    onStateChange(checkIn)

                    firestore
                        .collection("checkins")
                        .document("b")
                        .delete()
                }
            }
    }

}

data class CheckInDTO(
    val id: String,
    val giverId: String,
    var receiverId: String
)

private fun DocumentSnapshot.toCheckIn() =
    CheckInDTO(
        data?.get("id") as String,
        data?.get("giverId") as String,
        data?.get("receiverId") as String,
    )