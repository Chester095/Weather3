package com.geekbrains.weather.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.geekbrains.weather.model.*
import com.geekbrains.weather.view.App
import kotlin.random.Random

class DetailViewModel : ViewModel() {
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    private val repo: Repository = RepositoryImpl
    private val localRepo: LocalRepository = LocalRepositoryImpl(App.getHistoryDao())


    fun getData(): LiveData<AppState> = liveDataToObserve

    fun saveHistory(weather: Weather) {
        Log.d("!!! DetailViewModel", " saveHistory $weather")
        localRepo.saveEntity(weather)
    }

    internal fun getWeather() {
        liveDataToObserve.value = AppState.Loading

        Thread {
            if (Random.nextBoolean()) {
                val weather = repo.getWeatherFromServer()
                liveDataToObserve.postValue(AppState.Success(weather))
            } else {
                liveDataToObserve.postValue(AppState.Error(Exception("Проверь интернет")))
            }
        }.start()
    }

}