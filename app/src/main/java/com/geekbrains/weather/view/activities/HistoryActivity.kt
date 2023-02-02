package com.geekbrains.weather.view.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.weather.R
import com.geekbrains.weather.model.HistoryActivityAdapter
import com.geekbrains.weather.model.LocalRepositoryImpl
import com.geekbrains.weather.view.App

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionBar = supportActionBar
        actionBar!!.title = "История запросов"
        setContentView(R.layout.activity_history)
        Log.d("!!! HistoryActivity", " setContentView")

        findViewById<RecyclerView>(R.id.history_recycle_view).apply {
            Log.d("!!! HistoryActivity", " findViewById")
            // получаем данные из нашей БД
            Thread {
                adapter = HistoryActivityAdapter(LocalRepositoryImpl(App.getHistoryDao()).getAllHistory()).also {
                    it.notifyDataSetChanged()
                }
            }.start()

        }
        Log.d("!!! HistoryActivity", " adapter")
    }
}