package com.geekbrains.weather.model

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class WeatherService : Service() {

    companion object{
        const val TAG = "!!! WeatherService"
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

}