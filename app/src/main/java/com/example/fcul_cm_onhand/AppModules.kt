package com.example.fcul_cm_onhand

import com.example.fcul_cm_onhand.Services.FirestoreService
import com.example.fcul_cm_onhand.Services.IFirestoreService
import com.example.fcul_cm_onhand.repositories.AlertRepository
import com.example.fcul_cm_onhand.repositories.IAlertRepository
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
        fun provideService(firestoreService: FirestoreService): IFirestoreService

        @Binds
        @Singleton
        fun provideRepo(paintRepository: AlertRepository): IAlertRepository
    }
}