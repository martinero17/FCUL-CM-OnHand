package com.example.fcul_cm_onhand.services

import android.location.Location
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface ILocationService {
    suspend fun sendLocation(location: Location)
    suspend fun downloadLocation(): LocationDTO
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