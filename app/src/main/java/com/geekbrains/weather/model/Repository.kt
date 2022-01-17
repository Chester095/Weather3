package com.geekbrains.weather.model

// относится к Model так как отвечает за данные (как, что и где)

interface Repository {
    fun getWeatherFromServer(): Weather?
    fun getWeatherFromLocalStorageRus(): List<Weather>
    fun getWeatherFromLocalStorageWorld(): List<Weather>

    fun weatherLoaded(weather: Weather?)
    fun addLoadedListener(listener: OnLoadListener)
    interface OnLoadListener {
        fun onLoaded() {

        }
    }
}