package com.geekbrains.weather.model

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

// обычный BroadcastReceiver
class MainReceiver : BroadcastReceiver() {

    companion object {
        const val WEATHER_LOAD_SUCCESS = "WEATHER_LOAD_SUCCESS"
        const val WEATHER_LOAD_FAILED = "WEATHER_LOAD_FAILED"
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == WEATHER_LOAD_SUCCESS) {
            RepositoryImpl.weatherLoaded(intent.extras?.getParcelable("WEATHER_EXTRA"))
        } else if (intent.action == WEATHER_LOAD_FAILED) {
            RepositoryImpl.weatherLoaded(null)
        }
    }
}

class MainBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        StringBuilder().apply {
            append("СООБЩЕНИЕ ОТ СИСТЕМЫ\n")
            append("Action: ${intent.action}")
            toString()
        }
    }
}
