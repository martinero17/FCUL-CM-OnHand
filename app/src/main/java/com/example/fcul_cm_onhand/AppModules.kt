package com.example.fcul_cm_onhand

import com.example.fcul_cm_onhand.services.AlertService
import com.example.fcul_cm_onhand.services.IAlertService
import com.example.fcul_cm_onhand.repositories.AlertRepository
import com.example.fcul_cm_onhand.repositories.CheckInRepository
import com.example.fcul_cm_onhand.repositories.IAlertRepository
import com.example.fcul_cm_onhand.repositories.ICheckInRepository
import com.example.fcul_cm_onhand.services.CheckInService
import com.example.fcul_cm_onhand.services.ICheckInService
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModules {
    @Provides
    fun provideFirestore() = Firebase.firestore

    @Module
    @InstallIn(SingletonComponent::class)
    interface AppModuleInterface {
        @Binds
        @Singleton
        fun provideAlertService(alertService: AlertService): IAlertService

        @Binds
        @Singleton
        fun provideAlertRepo(alertRepository: AlertRepository): IAlertRepository

        @Binds
        @Singleton
        fun provideCheckInService(checkInService: CheckInService): ICheckInService

        @Binds
        @Singleton
        fun provideCheckInRepo(checkInRepository: CheckInRepository): ICheckInRepository
    }
}