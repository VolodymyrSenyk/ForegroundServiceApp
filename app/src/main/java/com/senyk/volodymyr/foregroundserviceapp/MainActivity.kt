package com.senyk.volodymyr.foregroundserviceapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onStartServiceClick()
    }

    fun onStartServiceClick() {
        val intent = Intent(applicationContext, ForegroundService::class.java)
        intent.action = ForegroundService.ACTION_START_FOREGROUND
        startService(intent)
    }

    fun onStopServiceClick() {
        val intent = Intent(applicationContext, ForegroundService::class.java)
        intent.action = ForegroundService.ACTION_STOP_FOREGROUND
        startService(intent)
    }
}
