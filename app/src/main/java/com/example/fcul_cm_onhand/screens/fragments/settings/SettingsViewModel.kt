package com.example.fcul_cm_onhand.screens.fragments.settings

import androidx.lifecycle.ViewModel
import com.example.fcul_cm_onhand.services.FirebaseAuthService

class SettingsViewModel(private val authService: FirebaseAuthService = FirebaseAuthService()): ViewModel() {
    fun logOut() {
        authService.logOut()
    }
}