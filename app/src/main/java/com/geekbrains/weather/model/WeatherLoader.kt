package com.geekbrains.weather.model

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.geekbrains.weather.BuildConfig
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

object WeatherLoader {

    fun load(city: City, listener: OnWeatherLoadListener) {

        val handler = Handler(Looper.myLooper() ?: Looper.getMainLooper())

        Thread {

            var urlConnection: HttpsURLConnection? = null

            //handler в него отправляем команду потоку от куда идёт выполнение
            val handler = Handler(Looper.getMainLooper())

            try {
                val uri = URL("https://api.weather.yandex.ru/v2/informers?Lat=${city.lat}&lon=${city.lon}")

                urlConnection = uri.openConnection() as HttpsURLConnection
                //передаём в urlConnection  наш ключ
                urlConnection.addRequestProperty("X-Yandex-API-Key", BuildConfig.WEATHER_API_KEY)
                //настраиваем urlConnection
                urlConnection.requestMethod = "GET"
                urlConnection.readTimeout = 1000
                urlConnection.connectTimeout = 1000
                //настраиваем ридер
                val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    reader.lines().collect(Collectors.joining("\n"))
                } else {
                    ""
                }

                Log.d("DEBUGLOG", "result: $result")

                //преобразует Gson в объект WeatherDTO
                val weatherDTO = Gson().fromJson(result, WeatherDTO::class.java)

                handler.post {
                    listener.onLoaded(weatherDTO)
                }

            } catch (e: Exception) {
                //любое обращение к listener надо завернуть в handler
                handler.post {
                    listener.onFailed(e)
                }

                Log.e("DEBUGLOG", "FAIL CONNECTION", e)
            } finally {
                urlConnection?.disconnect()
            }
        }.start()
    }

    interface OnWeatherLoadListener {
        //DTO data transfer object данные в формате Gson
        fun onLoaded(weatherDTO: WeatherDTO)
        fun onFailed(throwable: Throwable)
    }
}