package com.example.fcul_cm_onhand.model.alarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class ExactAlarmBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.v("BCAST_RECEIVER", "Time expired")
        TODO("Not yet implemented")
    }
}