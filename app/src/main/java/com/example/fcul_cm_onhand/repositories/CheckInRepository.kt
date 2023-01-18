package com.example.fcul_cm_onhand.repositories

import com.example.fcul_cm_onhand.services.CheckInDTO
import com.example.fcul_cm_onhand.services.ICheckInService
import com.google.firebase.firestore.ListenerRegistration
import javax.inject.Inject

interface ICheckInRepository {
    suspend fun sendCheckIn(checkIn: CheckInDTO)
    fun subscribeToAlerts(
        onSubscriptionError: (Exception) -> Unit,
        onStateChange: (checkin: CheckInDTO) -> Unit
    ): ListenerRegistration
}

class CheckInRepository @Inject constructor(private val service: ICheckInService): ICheckInRepository {
    override suspend fun sendCheckIn(checkIn: CheckInDTO) {
        service.sendCheckIn(checkIn)
    }

    override fun subscribeToAlerts(
        onSubscriptionError: (Exception) -> Unit,
        onStateChange: (checkin: CheckInDTO) -> Unit
    ): ListenerRegistration {
        return service.subscribeToCheckIn(onSubscriptionError, onStateChange)
    }
}