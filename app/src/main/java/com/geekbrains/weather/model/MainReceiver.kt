package com.geekbrains.weather.model

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

// обычный BroadcastReceiver
class MainReceiver : BroadcastReceiver() {

    companion object {
        const val WEATHER_LOAD_SUCCESS = "WEATHER_LOAD_SUCCESS"
        const val WEATHER_LOAD_FAILED = "WEATHER_LOAD_FAILED"
    }

    override fun onReceive(context: Context, intent: Intent) {

        Log.d("MainReceiver", "onReceive $intent}")

        when (intent.action) {
            WEATHER_LOAD_SUCCESS -> {
                RepositoryImpl.weatherLoaded(intent.extras?.getParcelable("WEATHER_EXTRA"))
                Log.d("WEATHER_LOAD_SUCCESS ", "intent.action"+   RepositoryImpl.weatherLoaded(intent.extras?.getParcelable("WEATHER_EXTRA")))
            }
            WEATHER_LOAD_FAILED -> {RepositoryImpl.weatherLoaded(null)
            Log.d("WEATHER_LOAD_FAILED ", "intent.action"+   intent.action)}
        }
    }
}

class MainBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        StringBuilder().apply {
            append("СООБЩЕНИЕ ОТ СИСТЕМЫ\n")
            append("Action: ${intent.action}")
            toString().also {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
    }
}