package com.example.fcul_cm_onhand.model.alarms

import androidx.compose.runtime.State

interface IExactAlarms {

    fun getExactAlarmState(): State<ExactAlarm>

    fun rescheduleAlarm()

    fun scheduleExactAlarm(exactAlarm: ExactAlarm, alarmType: ExactAlarmType)

    fun clearExactAlarm(alarmType: ExactAlarmType)

    fun canScheduleExactAlarms(): Boolean
}