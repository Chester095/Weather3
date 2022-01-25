package com.geekbrains.weather.model

import android.content.Context
import android.util.Log
import androidx.work.*
private const val TAG = "!!! MainWorker "

class MainWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    // worker в отличии от intentService  умеет возращать результат

    override fun doWork(): Result {
        Log.d(TAG, " doWork start")
        var result = Result.success()
        WeatherLoader.load(city = City(), object : WeatherLoader.OnWeatherLoadListener {
            override fun onLoaded(weatherDTO: WeatherDTO) {
                result = Result.success()
            }

            override fun onFailed(throwable: Throwable) {
                result = Result.failure()
            }

        })
        Log.d(TAG, " result = $result")
        return result
    }

    // запускается с помощью "других штук" ))
    companion object {
        fun startWorker(context: Context) {
            Log.d(TAG, " startWorker start")
            // OneTimeWorkRequest - один раз
            val uploadWorkRequest: WorkRequest =
                OneTimeWorkRequest.Builder(MainWorker::class.java) // можем критерии добавить при которых срабатывает загрузка
                    .setConstraints(
                        Constraints.Builder()
                            .setRequiredNetworkType(NetworkType.CONNECTED) // задача выполнится когда появится  интернет
                            .build()
                    ) // привязки
                    .build()
            // enqueue - запланировать вот такой  Request -> uploadWorkRequest
            WorkManager.getInstance(context).enqueue(uploadWorkRequest)
        }
    }

}