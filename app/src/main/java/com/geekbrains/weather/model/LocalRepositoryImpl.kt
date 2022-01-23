package com.geekbrains.weather.model

import java.util.*

class LocalRepositoryImpl(private val dao: HistoryDAO) : LocalRepository {

    override fun getAllHistory(): List<Weather> {
        return dao.all()
            // преобразуем из таблиц
            .map { entity ->
                Weather(
                    city = City(entity.city),
                    temperature = entity.temperature,
                    condition = entity.condition
                )

            }
    }

    override fun saveEntity(weather: Weather) {
        dao.insert(
            HistoryEntity(
                id = 0,
                city = weather.city.name,
                temperature = weather.temperature,
                condition = weather.condition,
                timestamp = Date().time
            )
        )
    }
}