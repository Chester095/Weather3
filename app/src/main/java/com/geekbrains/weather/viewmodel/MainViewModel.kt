package com.geekbrains.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.weather.model.Repository
import com.geekbrains.weather.model.RepositoryImpl

class MainViewModel : ViewModel() {
    // для хранения данных Mutable (то что можно изменить)
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    private val repo: Repository = RepositoryImpl
    var isRussian = true

    // будет возвращать данные
    fun getData(): LiveData<AppState> = liveDataToObserve

    fun getWeatherFromLocalStorageRus() = getDataFromLocalSource(true)
    fun getWeatherFromRemoteSource() = getDataFromLocalSource(isRussian)

    //
    fun getDataFromLocalSource(isRussian: Boolean = true)  {
        //изменяем state (статус данных)
        liveDataToObserve.value = AppState.Loading

        Thread {
            val weather = if (isRussian) {
                repo.getWeatherFromLocalStorageRus()
            } else {
                repo.getWeatherFromLocalStorageWorld()
            }

            liveDataToObserve.postValue(AppState.Success(weather))
        }.start()
    }
}