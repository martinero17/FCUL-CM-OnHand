package com.example.fcul_cm_onhand.model.alarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import com.example.fcul_cm_onhand.OnHandApplication
import com.example.fcul_cm_onhand.R
import com.example.fcul_cm_onhand.repositories.IAlertRepository
import com.example.fcul_cm_onhand.services.AlertDTO
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

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
        var triggerTime = SystemClock.elapsedRealtime() + 10000
        application.exactAlarms.scheduleExactAlarm(ExactAlarm(triggerTime), ExactAlarmType.CHECK_IN_TIMEOUT)

        //Reschedule for the next day
        application.exactAlarms.clearExactAlarm(ExactAlarmType.CHECK_IN)
        triggerTime = convertToAlarmTimeMillis(11, 5)
        application.exactAlarms.scheduleExactAlarm(ExactAlarm(triggerTime), ExactAlarmType.CHECK_IN)
    }
}

@AndroidEntryPoint
class CheckInTimeoutAlarmBroadcastReceiver @Inject constructor(): BroadcastReceiver() {

    @Inject
    lateinit var repo: IAlertRepository

    override fun onReceive(context: Context, intent: Intent?) {
        val application = (context.applicationContext as OnHandApplication)

        val scope = CoroutineScope(Dispatchers.IO)

        showNotification(
            context,
            application.getString(R.string.checkin_channel_id),
            application.getString(R.string.checkin_channel_name),
            Random().nextInt(),
            "Check-in timedout, alarm sent to care giver!"
        )

        scope.launch {
            repo.sendAlert(AlertDTO(UUID.randomUUID().toString(), "b", "c", "AUTOMATIC"))
        }
    }
}