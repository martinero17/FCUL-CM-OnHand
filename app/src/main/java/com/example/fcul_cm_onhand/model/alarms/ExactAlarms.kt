package com.example.fcul_cm_onhand.model.alarms

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

const val CHECK_IN_REQUEST_CODE = 1001
const val CHECK_IN_TIMEOUT_REQUEST_CODE = 1002

class ExactAlarms(
    private val context: Context,
    private val sharedPreferences: SharedPreferences
): IExactAlarms {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private var exactAlarmState = mutableStateOf(ExactAlarm.NOT_SET)

    override fun getExactAlarmState(): State<ExactAlarm> {
        TODO("Not yet implemented")
    }

    override fun rescheduleAlarm() {
        val alarm: ExactAlarm = sharedPreferences.getExactAlarm()
        if (alarm.isSet() && alarm.isNotInPast() && canScheduleExactAlarms()) {
            scheduleExactAlarm(alarm, ExactAlarmType.CHECK_IN)
        } else {
            clearExactAlarm(ExactAlarmType.CHECK_IN)
        }
    }

    override fun scheduleExactAlarm(exactAlarm: ExactAlarm, alarmType: ExactAlarmType) {
        if (alarmType == ExactAlarmType.CHECK_IN)
            setExactAlarmSetExactAndAllowWhileIdle(exactAlarm.triggerAtMillis, alarmType)
        else if (alarmType == ExactAlarmType.CHECK_IN_TIMEOUT)
            setExactAlarmSetTimeoutAndAllowWhileIdle(exactAlarm.triggerAtMillis, alarmType)
        sharedPreferences.putExactAlarm(exactAlarm)
        exactAlarmState.value = exactAlarm
    }

    override fun clearExactAlarm(alarmType: ExactAlarmType) {
        val pendingIntent = createExactAlarmIntent(alarmType)
        alarmManager.cancel(pendingIntent)

        sharedPreferences.clearExactAlarm()
        exactAlarmState.value = ExactAlarm.NOT_SET
    }

    @SuppressLint("MissingPermission")
    private fun setExactAlarmSetExactAndAllowWhileIdle(
        triggerAtMillis: Long,
        alarmType: ExactAlarmType
    ) {
        val pendingIntent = createExactAlarmIntent(alarmType)
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            pendingIntent
        )
    }

    @SuppressLint("MissingPermission")
    private fun setExactAlarmSetTimeoutAndAllowWhileIdle(
        triggerAtMillis: Long,
        alarmType: ExactAlarmType
    ) {
        val pendingIntent = createExactAlarmIntent(alarmType)
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            triggerAtMillis,
            pendingIntent
        )
    }

    private fun createExactAlarmIntent(alarmType: ExactAlarmType): PendingIntent {
        val intent = Intent(context, BroadcastReceiverFromAlarmType(alarmType))
        return PendingIntent.getBroadcast(
            context,
            requestCodeFromAlarmType(alarmType),
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    override fun canScheduleExactAlarms(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
            true
        }
    }
}

enum class ExactAlarmType {
    CHECK_IN,
    CHECK_IN_TIMEOUT
}

fun requestCodeFromAlarmType(alarmType: ExactAlarmType): Int {
    return when (alarmType) {
        ExactAlarmType.CHECK_IN -> CHECK_IN_REQUEST_CODE
        ExactAlarmType.CHECK_IN_TIMEOUT -> CHECK_IN_TIMEOUT_REQUEST_CODE
    }
}

fun BroadcastReceiverFromAlarmType(alarmType: ExactAlarmType): Class<*> {
    return when (alarmType) {
        ExactAlarmType.CHECK_IN -> CheckInAlarmBroadcastReceiver::class.java
        ExactAlarmType.CHECK_IN_TIMEOUT -> CheckInTimeoutAlarmBroadcastReceiver::class.java
    }
}

