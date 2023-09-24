package com.geekbrains.weather.model

import androidx.room.*

@Dao
interface HistoryDAO {

    // получить все данные из БД
    // "ORDER BY timestamp DESC" - отсортировать по полю timestamp в обратном порядке
    @Query("SELECT * FROM HistoryEntity ORDER BY timestamp DESC")
    fun getAll(): List<HistoryEntity>

    // данные по городу
    @Query("SELECT * FROM HistoryEntity WHERE city LIKE :city")
    fun getHistoryByCity(city: String): List<HistoryEntity>

    // добавить данные
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: HistoryEntity)

    @Update
    fun update(entity: HistoryEntity)

    @Delete
    fun delete(entity: HistoryEntity)
}
