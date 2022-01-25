package com.geekbrains.weather.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.weather.R
import com.geekbrains.weather.model.HistoryAdapter
import com.geekbrains.weather.model.LocalRepositoryImpl

class HistoryActivity : AppCompatActivity() {
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        Log.d("!!! HistoryActivity", " setContentView")

        findViewById<RecyclerView>(R.id.history_recycle_view).apply {
            Log.d("!!! HistoryActivity", " findViewById")
            // получаем данные из нашей БД
            Thread {
                adapter = HistoryAdapter(LocalRepositoryImpl(App.getHistoryDao()).getAllHistory()).also {
                    it.notifyDataSetChanged()
                }
            }.start()

        }
        Log.d("!!! HistoryActivity", " adapter")
    }
}