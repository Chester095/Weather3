package com.geekbrains.weather.model

import android.content.Context
import androidx.work.*

class MainWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    // worker в отличии от intentService  умеет возращщать результат
    override fun doWork(): Result {
        var result = Result.success()
        WeatherLoader.load(city = City(), object : WeatherLoader.OnWeatherLoadListener {
            override fun onLoaded(weatherDTO: WeatherDTO) {
                result = Result.success()
            }

            override fun onFailed(throwable: Throwable) {
                result = Result.failure()
            }

        })
        return result
    }

    // запускается с помощью "других штук" ))
    companion object {
        fun startWorker(context: Context) {
            // OneTimeWorkRequest - один раз
            val uploadWorkRequest: WorkRequest =
                OneTimeWorkRequest.Builder(MainWorker::class.java) // можем критерии добавить при которых срабатывает загрузка
                    .setConstraints(
                        Constraints.Builder()
                            .setRequiredNetworkType(NetworkType.CONNECTED) // задача выполнится когда пояаится  интернет
                            .build()
                    ) // привязки
                    .build()
            // enqueue - запланировать вот такой  Request -> uploadWorkRequest
            WorkManager.getInstance(context).enqueue(uploadWorkRequest)
        }
    }

}