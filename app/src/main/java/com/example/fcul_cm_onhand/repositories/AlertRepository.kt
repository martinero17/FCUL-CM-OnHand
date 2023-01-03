package com.example.fcul_cm_onhand.repositories

import com.example.fcul_cm_onhand.Services.AlertDTO
import com.example.fcul_cm_onhand.Services.IAlertService
import javax.inject.Inject

interface IAlertRepository {
    suspend fun sendAlert(alert: AlertDTO)
}

class AlertRepository @Inject constructor(private val service: IAlertService): IAlertRepository  {
    override suspend fun sendAlert(alert: AlertDTO) {
        service.sendAlert(alert)
    }

}