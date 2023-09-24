package com.geekbrains.weather.viewmodel

import androidx.lifecycle.ViewModel
import com.geekbrains.weather.model.LocalRepository
import com.geekbrains.weather.model.LocalRepositoryImpl
import com.geekbrains.weather.model.Weather
import com.geekbrains.weather.view.App

class DetailViewModel : ViewModel() {
    private val localRepo: LocalRepository = LocalRepositoryImpl(App.getHistoryDao())

    fun saveHistory(weather: Weather) {
        localRepo.saveEntity(weather)
    }

}