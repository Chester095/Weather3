package com.geekbrains.weather.model

import androidx.room.Database
import androidx.room.Entity
import androidx.room.RoomDatabase


// указываем что это БД и какие в ней будут элементы, номер версии структуры и буличность данных
@Database(entities = arrayOf(HistoryEntity::class), version = 1, exportSchema = false)
abstract class HistoryDB : RoomDatabase() {

    abstract fun historyDAO():HistoryDAO
}