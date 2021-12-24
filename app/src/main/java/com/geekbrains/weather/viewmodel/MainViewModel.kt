package com.geekbrains.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.weather.model.Repository
import com.geekbrains.weather.model.RepositoryImpl
import kotlin.random.Random

class MainViewModel : ViewModel() {
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    private val repo: Repository = RepositoryImpl()
    fun getData(): LiveData<AppState> = liveDataToObserve

    fun getWeatherFromLocalStorageRus() = getDataFromLocalSource(true)
    fun getWeatherFromLocalStorageWorld() = getDataFromLocalSource(false)
    fun getWeatherFromRemoteSource() = getDataFromLocalSource(true)

    private fun getDataFromLocalSource(isRussian: Boolean = true) {
        liveDataToObserve.value = AppState.Loading

        Thread {
            Thread.sleep(1000)

            val weather = if (isRussian) {
                repo.getWeatherFromLocalStorageRus()
            } else {
                repo.getWeatherFromLocalStorageWorld()
            }

            liveDataToObserve.postValue(AppState.Success(weather))
/*            liveDataToObserve.postValue(
                AppState.Success(

            )*/
        }.start()
    }


}