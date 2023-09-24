package com.geekbrains.weather.view

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.geekbrains.weather.model.HistoryDAO
import com.geekbrains.weather.model.HistoryDB

// при создании Application будем создавать нашу БД
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        // инициализация appInstance
        appInstance = this

    }

    // будем хранить БД в статическом
    companion object {
        private var appInstance: App? = null

        // сама БД
        private var db: HistoryDB? = null

        // название нашей БД
        private const val DB_NAME = "History.db"

        // инициализация БД
        fun getHistoryDao(): HistoryDAO {
            if (db == null) {
                synchronized(HistoryDB::class.java) {
                    if (db == null) {
                        appInstance?.let { app ->
                            db = Room.databaseBuilder(
                                app.applicationContext,
                                HistoryDB::class.java,
                                DB_NAME
                            ).build()
                        } ?: throw Exception("Что-то пошло не так")
                    }
                }
            }
            return db!!.historyDAO()
        }

    }


}