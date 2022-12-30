package com.example.fcul_cm_onhand.repositories

import com.example.fcul_cm_onhand.Services.IFirestoreService
import javax.inject.Inject

interface IAlertRepository {

}

class AlertRepository @Inject constructor(private val service: IFirestoreService): IAlertRepository  {

}