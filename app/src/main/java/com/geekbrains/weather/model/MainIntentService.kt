package com.geekbrains.weather.model

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.geekbrains.weather.R

class MainIntentService : IntentService("MainIntentService") {

    companion object {
        const val TAG = "MainIntentService"
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, "current thread : " + Thread.currentThread().name)

        // надо получить данные
        intent?.getParcelableExtra<Weather>(getString(R.string.weather_extra))?.let { weather ->
            WeatherLoader.load(weather.city, object : WeatherLoader.OnWeatherLoadListener {
                override fun onLoaded(weatherDTO: WeatherDTO) {
                    // после загрузки надо от нашего контекста...
                    applicationContext.sendBroadcast(Intent(applicationContext, MainReceiver::class.java).apply {
                        action = MainReceiver.WEATHER_LOAD_SUCCESS
                        Log.d(TAG, "WEATHER_LOAD_SUCCESS")
                        Log.d(TAG, "WEATHER_LOAD_SUCCESS " + weatherDTO.fact?.temp)
                        Log.d(TAG, "WEATHER_LOAD_SUCCESS " + weatherDTO.fact?.feelsLike)
                        Log.d(TAG, "WEATHER_LOAD_SUCCESS " + weatherDTO.fact?.condition)
                        putExtra(getString(R.string.weather_extra), Weather(
                                temperature = weatherDTO.fact?.temp ?: 0,
                                feelsLike = weatherDTO.fact?.feelsLike ?: 0,
                                condition = weatherDTO.fact?.condition ?: ""
                            )
                        )
                    })
                }

                override fun onFailed(throwable: Throwable) {

                    applicationContext.sendBroadcast(Intent(applicationContext, MainReceiver::class.java).apply {
                        action = MainReceiver.WEATHER_LOAD_FAILED
                    })
                }

            })
        }
    }


    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand $intent}")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }
}