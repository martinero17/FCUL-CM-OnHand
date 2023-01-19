package com.example.fcul_cm_onhand.repositories

import android.location.Location
import com.example.fcul_cm_onhand.services.ILocationService
import com.example.fcul_cm_onhand.services.LocationDTO
import javax.inject.Inject

interface ILocationRepository {
    suspend fun sendLocation(location: Location)
    suspend fun downloadLocation(): LocationDTO
}

class LocationRepository @Inject constructor(private val service: ILocationService): ILocationRepository {
    override suspend fun sendLocation(location: Location) {
        service.sendLocation(location)
    }

    override suspend fun downloadLocation(): LocationDTO {
        return service.downloadLocation()
    }

}