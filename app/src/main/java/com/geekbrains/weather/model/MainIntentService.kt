package com.geekbrains.weather.model

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.geekbrains.weather.R
import com.geekbrains.weather.view.fragments.DetailFragment.Companion.TEST_BROADCAST_INTENT_FILTER
import com.geekbrains.weather.view.fragments.DetailFragment.Companion.THREADS_FRAGMENT_BROADCAST_EXTRA

class MainIntentService : IntentService("MainIntentService") {

    companion object {
        const val MAIN_SERVICE_INT_EXTRA = "MainServiceIntExtra"
    }

    //Отправка уведомления о завершении сервиса
    private fun sendBack(result: String) {
        val broadcastIntent = Intent(TEST_BROADCAST_INTENT_FILTER)
        broadcastIntent.putExtra(THREADS_FRAGMENT_BROADCAST_EXTRA, result)
        sendBroadcast(broadcastIntent)
    }

    override fun onHandleIntent(intent: Intent?) {
        intent?.let {
            sendBack(it.getIntExtra(MAIN_SERVICE_INT_EXTRA, 0).toString())
        }


        // надо получить данные
        intent?.getParcelableExtra<Weather>(getString(R.string.weather_extra))?.let { weather ->
            WeatherLoader.loadRetrofit(weather.city, object : WeatherLoader.OnWeatherLoadListener {
                override fun onLoaded(weatherDTO: WeatherDTO) {
                    // после загрузки надо от нашего контекста...
                    applicationContext.sendBroadcast(Intent(applicationContext, MainReceiver::class.java).apply {
                        action = MainReceiver.WEATHER_LOAD_SUCCESS
                        putExtra(
                            getString(R.string.weather_extra), Weather(
                                city = weather.city,
                                temperature = weatherDTO.fact?.temp ?: 0,
                                feelsLike = weatherDTO.fact?.feelsLike ?: 0,
                                condition = weatherDTO.fact?.condition ?: "",
                                icon = weatherDTO.fact?.icon ?: "0"
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


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

}