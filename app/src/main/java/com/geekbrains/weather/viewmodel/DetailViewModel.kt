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

//    fun getWeatherFromLocalStorageRus() = repositoryImpl.getDataFromLocalSource(isRussian = true)
//    fun getWeatherFromLocalStorageWorld() = getDataFromLocalSource(isRussian = false)
//    fun getWeatherFromRemoteSource() = getDataFromLocalSource(isRussian = true)

    internal fun getWeather() {
        liveDataToObserve.value = AppState.Loading

        Thread {
            Thread.sleep(1000)

            if (Random.nextBoolean()) {
                val weather = repo.getWeatherFromServer()
                liveDataToObserve.postValue(AppState.Success(weather))
            } else {
                liveDataToObserve.postValue(AppState.Error(Exception("Проверь интернет")))
            }
/*            liveDataToObserve.postValue(
                AppState.Success(
                    if (isRussian)
                        repositoryImpl.getWeatherFromLocalStorageRus() else
                        repositoryImpl.getWeatherFromLocalStorageWorld()
                )
            )*/
        }.start()
    }

}