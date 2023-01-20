package com.example.fcul_cm_onhand.model.alarms

import java.util.*


fun Calendar.setHourAndMinute(hour: Int, minute: Int): Calendar {
    set(Calendar.HOUR_OF_DAY, hour)
    set(Calendar.MINUTE, minute)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)

    return this
}

fun Long.plusOneDay(): Long = this + 24 * 60 * 60 * 1000

fun currentTimeMillis(): Long = Calendar.getInstance().timeInMillis
