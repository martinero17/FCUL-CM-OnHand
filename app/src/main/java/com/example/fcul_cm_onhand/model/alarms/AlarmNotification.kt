package com.example.fcul_cm_onhand.model.alarms

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.fcul_cm_onhand.OnHandApplication
import com.example.fcul_cm_onhand.R
import com.example.fcul_cm_onhand.services.CheckInDTO
import com.example.fcul_cm_onhand.services.ICheckInService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@SuppressLint("LaunchActivityFromNotification")
fun showNotification(
    context: Context,
    channelId: String,
    channelName: String,
    notificationId: Int,
    contentTitle: String
) {
    val startAppIntent = Intent(context, AlarmNotificationClickedBroadcastReceiver::class.java)
    val CheckInIntent = PendingIntent.getBroadcast(
        context,
        0,
        startAppIntent,
        PendingIntent.FLAG_IMMUTABLE
    )

    val notificationBuilder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.logo)
        .setContentTitle(contentTitle)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(CheckInIntent)
        .setAutoCancel(true)
    val notification = notificationBuilder.build()
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
        && notificationManager.getNotificationChannel(channelId) == null
    ) {
        notificationManager.createNotificationChannel(
            NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
        )
    }

    notificationManager.notify(notificationId, notification)
}

@AndroidEntryPoint
class AlarmNotificationClickedBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var repo: ICheckInService

    override fun onReceive(context: Context, intent: Intent) {
        val application = (context.applicationContext as OnHandApplication)

        val scope = CoroutineScope(Dispatchers.IO)

        scope.launch {
            repo.sendCheckIn(CheckInDTO(UUID.randomUUID().toString(), "b", "c"))
        }

        application.exactAlarms.clearExactAlarm(ExactAlarmType.CHECK_IN_TIMEOUT)
    }
}