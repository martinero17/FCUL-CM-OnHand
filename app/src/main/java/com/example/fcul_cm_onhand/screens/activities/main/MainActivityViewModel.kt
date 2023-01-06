package com.example.fcul_cm_onhand.screens.activities.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fcul_cm_onhand.Services.AlertDTO
import com.example.fcul_cm_onhand.model.UserType
import com.example.fcul_cm_onhand.repositories.IAlertRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(): ViewModel() {

    @Inject
    lateinit var repo: IAlertRepository

    var userType: UserType? = null

    fun sendAlert() {

        viewModelScope.launch(Dispatchers.IO) {
            repo.sendAlert(AlertDTO("a", "b", "c", "d"))
        }

    }
}