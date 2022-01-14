package com.geekbrains.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.weather.model.Repository
import com.geekbrains.weather.model.RepositoryImpl
import kotlin.random.Random

class DetailViewModel : ViewModel() {
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    private val repo: Repository = RepositoryImpl()
    fun getData(): LiveData<AppState> = liveDataToObserve


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