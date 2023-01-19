package com.example.fcul_cm_onhand.services

import android.content.ContentValues
import android.location.Location
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface ILocationService {
    suspend fun sendLocation(location: Location)
    suspend fun downloadLocation(): LocationDTO
    fun subscribeToLocation(
        onSubscriptionError: (Exception) -> Unit,
        onStateChange: () -> Unit
    ): ListenerRegistration
}

class LocationService @Inject constructor(private val firestore: FirebaseFirestore): ILocationService {
    override suspend fun sendLocation(location: Location) {
        val locationDto = LocationDTO(location.latitude, location.longitude)

        firestore
            .collection("locations")
            .document("c")
            .set(locationDto)
            .await()
    }

    override suspend fun downloadLocation(): LocationDTO {
        return firestore
            .collection("locations")
            .get()
            .await()
            .documents
            .first()
            .toLocation()
    }

    override fun subscribeToLocation(
        onSubscriptionError: (Exception) -> Unit,
        onStateChange: () -> Unit
    ): ListenerRegistration {
        return firestore
            .collection("locations")
            .document("c")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                onStateChange()
            }
    }
}

data class LocationDTO(
    val lat: Double,
    val lng: Double,
)

private fun DocumentSnapshot.toLocation() =
    LocationDTO(
        data?.get("lat") as Double,
        data?.get("lng") as Double,
    )