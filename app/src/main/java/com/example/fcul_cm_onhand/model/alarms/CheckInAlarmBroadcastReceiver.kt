package com.example.fcul_cm_onhand.model.alarms

import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.util.Log
import com.example.fcul_cm_onhand.OnHandApplication
import com.example.fcul_cm_onhand.R
import java.util.*

class CheckInAlarmBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val application = (context.applicationContext as OnHandApplication)

        showNotification(
            context,
            application.getString(R.string.checkin_channel_id),
            application.getString(R.string.checkin_channel_name),
            Random().nextInt(),
            "Check-in needed!"
        )

        //Set timeout alarm
        application.exactAlarms.scheduleExactAlarm(ExactAlarm(SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_HALF_HOUR), ExactAlarmType.CHECK_IN_TIMEOUT)

        //Reschedule for the next day
        val triggerTime = convertToAlarmTimeMillis(12, 0)
        application.exactAlarms.scheduleExactAlarm(ExactAlarm(triggerTime), ExactAlarmType.CHECK_IN)
    }
}

class CheckInTimoutAlarmBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        Log.v("BCAST_RECEIVER", "Time expired")
    }
}