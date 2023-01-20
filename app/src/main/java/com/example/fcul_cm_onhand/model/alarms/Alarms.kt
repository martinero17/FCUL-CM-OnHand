package com.example.fcul_cm_onhand.model.alarms

import android.content.SharedPreferences
import java.util.*

private const val EXACT_ALARM_PREFERENCES_KEY = "exact_alarm"

private const val ALARM_NOT_SET = -1L

fun SharedPreferences.getExactAlarm(): ExactAlarm {
    val triggerAtMillis = getLong(EXACT_ALARM_PREFERENCES_KEY, ALARM_NOT_SET)
    return ExactAlarm(triggerAtMillis)
}

fun SharedPreferences.putExactAlarm(exactAlarm: ExactAlarm) {
    edit().putLong(EXACT_ALARM_PREFERENCES_KEY, exactAlarm.triggerAtMillis).apply()
}

fun SharedPreferences.clearExactAlarm() {
    edit().putLong(EXACT_ALARM_PREFERENCES_KEY, ALARM_NOT_SET).apply()
}

fun convertToAlarmTimeMillis(hour: Int, minute: Int): Long {
    val calendar = Calendar.getInstance()
    val currentTimeMillis = calendar.timeInMillis
    val proposedTimeMillis = calendar.setHourAndMinute(hour, minute).timeInMillis

    val alarmTimeMillis: Long = if (proposedTimeMillis > currentTimeMillis) {
        proposedTimeMillis
    } else {
        proposedTimeMillis.plusOneDay()
    }

    return alarmTimeMillis
}

data class ExactAlarm(val triggerAtMillis: Long) {

    companion object {

        val NOT_SET = ExactAlarm(ALARM_NOT_SET)
    }

    fun isSet(): Boolean = triggerAtMillis != ALARM_NOT_SET

    fun isNotInPast(): Boolean = triggerAtMillis > currentTimeMillis()
}
