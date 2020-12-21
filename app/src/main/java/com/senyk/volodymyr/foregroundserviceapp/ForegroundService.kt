package com.senyk.volodymyr.foregroundserviceapp

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.util.*

class ForegroundService : Service() {

    private lateinit var notificationCreator: ForegroundServiceNotificationCreator

    private val timer = Timer()

    override fun onBind(intent: Intent?): IBinder = throw UnsupportedOperationException()

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        when (intent.action) {
            ACTION_START_FOREGROUND -> startService()
            ACTION_STOP_FOREGROUND -> stopService()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startService() {
        notificationCreator = ForegroundServiceNotificationCreator(applicationContext)
        startForeground(SERVICE_ID, notificationCreator.createNotification())
        timer.schedule(SayHelloTimerTask(), 0, SAY_HELLO_PERIOD_IN_MILLIS)
    }

    private fun stopService() {
        stopForeground(true)
        timer.cancel()
        stopSelf()
    }

    companion object {
        private const val SERVICE_ID = 1
        private const val SAY_HELLO_PERIOD_IN_MILLIS = 1000L * 2 // 2 seconds

        const val ACTION_START_FOREGROUND = "ACTION_START_FOREGROUND"
        const val ACTION_STOP_FOREGROUND = "ACTION_STOP_FOREGROUND"
    }
}
