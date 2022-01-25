package com.geekbrains.weather.model

import android.util.Log

object RepositoryImpl : Repository {

    private const val TAG = "!!! RepositoryImpl "
    private val listeners: MutableList<Repository.OnLoadListener> = mutableListOf()
    private var weather: Weather? = null

    override fun getWeatherFromServer(): Weather? = weather

    override fun getWeatherFromLocalStorageRus(): List<Weather> = getRussianCities()

    override fun getWeatherFromLocalStorageWorld(): List<Weather> = getWorldCities()

    override fun weatherLoaded(weather: Weather?) {
        this.weather = weather
        // уведомляем все Listener, что погода получена
        listeners.forEach {
            it.onLoaded()
            Log.d(TAG, "weatherLoaded listener = $listeners")
        }
    }

    override fun addLoadedListener(listener: Repository.OnLoadListener) {
        Log.d(TAG, "addLoadedListener listener = $listener")
        listeners.add(listener)
    }

    override fun removeLoadedListener(listener: Repository.OnLoadListener) {
        Log.d(TAG, "removeLoadedListener listener = $listener")
        listeners.remove(listener)
    }


}