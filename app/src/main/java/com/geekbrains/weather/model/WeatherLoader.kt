package com.geekbrains.weather.model

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.geekbrains.weather.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

object WeatherLoader {

    fun load(city: City, listener: OnWeatherLoadListener) {

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
            listener.onLoaded(weatherDTO)
        } catch (e: Exception) {
            listener.onFailed(e)
            Log.e("DEBUGLOG", "FAIL CONNECTION", e)
        } finally {
            urlConnection?.disconnect()
        }
    }


    // создаём клиента okHttp
    val client: OkHttpClient = OkHttpClient.Builder()
        .callTimeout(1000, TimeUnit.MILLISECONDS)
        .connectTimeout(1000, TimeUnit.MILLISECONDS)

        // убираем addHeader из WeatherApi и добавляем во все наши запросы ключ API
        // chain - цепочка вызова
        .addInterceptor(Interceptor { chain ->
            chain.proceed(
                chain.request()
                    .newBuilder()
                    .addHeader("X-Yandex-API-Key", BuildConfig.WEATHER_API_KEY)
                    .build()
            )
        })
        // добавляем логирование
        .addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })

        .build()
    private val weatherAPI: WeatherAPI = Retrofit.Builder()
        .baseUrl("https://api.weather.yandex.ru/")
        .client(client)
        // как преобразовывать
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()

        // retrofit за нас сгенировал методы WeatherAPI
        .create(WeatherAPI::class.java)

    fun loadRetrofit(city: City, listener: OnWeatherLoadListener) {

        weatherAPI.getWeather(city.lat, city.lon)
            .enqueue(object : retrofit2.Callback<WeatherDTO> {
                override fun onResponse(
                    call: retrofit2.Call<WeatherDTO>,
                    response: retrofit2.Response<WeatherDTO>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { listener.onLoaded(it) }
                    } else {
                        listener.onFailed(Exception(response.message()))
                        Log.e("DEBUGLOG", "FAIL CONNECTION  $response")
                    }
                }

                override fun onFailure(call: retrofit2.Call<WeatherDTO>, t: Throwable) {
                    listener.onFailed(t)
                }
            })
    }

    fun loadOkHttp(city: City, listener: OnWeatherLoadListener) {
        // создаём клиенту запрос, чтобы было потом что спрашивать
        val request: Request = Request.Builder()
            .get()
            .addHeader("X-Yandex-API-Key", BuildConfig.WEATHER_API_KEY)
            .url("https://api.weather.yandex.ru/v2/informers?Lat=${city.lat}&lon=${city.lon}")
            .build()

        // у клиента спрашиываем запрос
        client.newCall(request)
            // execute - в этом же потоке, enqueue в новом
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    listener.onFailed(e)
                    Log.e("DEBUGLOG", "FAIL CONNECTION", e)
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val weatherDTO = Gson().fromJson(response.body?.string(), WeatherDTO::class.java)
                        listener.onLoaded(weatherDTO)
                    } else {
                        listener.onFailed(Exception(response.body?.string()))
                        Log.e("DEBUGLOG", "FAIL CONNECTION  $response")
                    }
                }
            })

    }

    interface OnWeatherLoadListener {
        //DTO data transfer object данные в формате Gson
        fun onLoaded(weatherDTO: WeatherDTO)
        fun onFailed(throwable: Throwable)
    }
}