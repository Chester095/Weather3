package com.geekbrains.weather.model

class RepositoryImpl : Repository {
    override fun getWeatherFromServer(): Weather = Weather()
    //TODO 2 урок 02:32:00

    override fun getWeatherFromLocalStorageRus(): List<Weather> = getRussianCities()

    override fun getWeatherFromLocalStorageWorld(): List<Weather> = getWorldCities()

}