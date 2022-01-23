package com.geekbrains.weather.model

interface LocalRepository {

    fun getAllHistory() : List<Weather>

    fun saveEntity(weather: Weather)
}