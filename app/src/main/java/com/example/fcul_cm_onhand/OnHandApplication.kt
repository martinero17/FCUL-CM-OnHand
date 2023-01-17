package com.example.fcul_cm_onhand

import android.app.Application
import android.content.res.Resources
import com.example.fcul_cm_onhand.model.alarms.ExactAlarm
import com.example.fcul_cm_onhand.model.alarms.ExactAlarms
import dagger.hilt.android.HiltAndroidApp

const val SHARED_PREFS = "alarms"

@HiltAndroidApp
class OnHandApplication : Application() {
    companion object {
        lateinit var instance: Application
        lateinit var resourses: Resources
    }

    lateinit var exactAlarms: ExactAlarms

    override fun onCreate() {
        super.onCreate()
        instance = this
        resourses = resources

        val sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        exactAlarms = ExactAlarms(this, sharedPreferences)
    }
}