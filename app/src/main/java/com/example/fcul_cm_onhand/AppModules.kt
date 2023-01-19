package com.example.fcul_cm_onhand

import com.example.fcul_cm_onhand.repositories.*
import com.example.fcul_cm_onhand.services.*
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

        @Binds
        @Singleton
        fun provideLocationService(locationService: LocationService): ILocationService

        @Binds
        @Singleton
        fun provideLocationRepo(locationRepository: LocationRepository): ILocationRepository
    }
}