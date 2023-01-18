package com.example.fcul_cm_onhand.model.alarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.fcul_cm_onhand.OnHandApplication
import com.example.fcul_cm_onhand.R
import java.util.*

class ExactAlarmBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        Log.v("BCAST_RECEIVER", "Time expired")

        val application = (context.applicationContext as OnHandApplication)

        showNotification(
            context,
            application.getString(R.string.checkin_channel_id),
            application.getString(R.string.checkin_channel_name),
            Random().nextInt(),
            "Check-in needed!"
        )

        //Reschedule for the next day
        val triggerTime = convertToAlarmTimeMillis(12, 0)
        application.exactAlarms.scheduleExactAlarm(ExactAlarm(triggerTime))
    }
}