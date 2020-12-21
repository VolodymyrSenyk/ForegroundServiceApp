package com.senyk.volodymyr.foregroundserviceapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

class ForegroundServiceNotificationCreator(private val context: Context) {

    fun createNotification(): Notification {
        setNotificationChannel(context)
        return getNotification(context, getStopServiceIntent(context))
    }

    private fun setNotificationChannel(context: Context) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val priority = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(
                CHANNEL_FOREGROUND_NAME,
                CHANNEL_NAME,
                priority
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun getNotification(context: Context, stopIntent: PendingIntent?): Notification {
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_FOREGROUND_NAME)
            .setSmallIcon(android.R.drawable.ic_menu_more)
            .setContentTitle(context.getString(R.string.service_name))
            .setOngoing(true)

        stopIntent?.let { it ->
            notificationBuilder.addAction(
                android.R.drawable.ic_menu_close_clear_cancel,
                context.getString(R.string.stop_service), it
            )
        }

        return notificationBuilder.build()
    }

    private fun getStopServiceIntent(context: Context): PendingIntent {
        val stopServiceIntent = Intent(context, ForegroundService::class.java)
        stopServiceIntent.action =
            ForegroundService.ACTION_STOP_FOREGROUND
        return PendingIntent.getService(
            context,
            REQUEST_CODE,
            stopServiceIntent,
            FLAGS
        )
    }

    companion object {

        private const val CHANNEL_FOREGROUND_NAME = "com.senyk.volodymyr.timetracker"
        private const val CHANNEL_NAME = "timetracker"

        private const val REQUEST_CODE = 0
        private const val FLAGS = 0
    }
}
