package com.example.fcul_cm_onhand.repositories

import android.location.Location
import com.example.fcul_cm_onhand.services.CheckInDTO
import com.example.fcul_cm_onhand.services.ILocationService
import com.example.fcul_cm_onhand.services.LocationDTO
import com.google.firebase.firestore.ListenerRegistration
import javax.inject.Inject

interface ILocationRepository {
    suspend fun sendLocation(location: Location)
    suspend fun downloadLocation(): LocationDTO
    fun subscribeToLocation(
        onSubscriptionError: (Exception) -> Unit,
        onStateChange: () -> Unit
    ): ListenerRegistration
}

class LocationRepository @Inject constructor(private val service: ILocationService): ILocationRepository {
    override suspend fun sendLocation(location: Location) {
        service.sendLocation(location)
    }

    override suspend fun downloadLocation(): LocationDTO {
        return service.downloadLocation()
    }

    override fun subscribeToLocation(
        onSubscriptionError: (Exception) -> Unit,
        onStateChange: () -> Unit
    ): ListenerRegistration {
        return service.subscribeToLocation(onSubscriptionError, onStateChange)
    }

}