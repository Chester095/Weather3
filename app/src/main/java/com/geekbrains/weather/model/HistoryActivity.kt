package com.geekbrains.weather.model

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.weather.R
import com.geekbrains.weather.view.App

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        findViewById<RecyclerView>(R.id.history_recycle_view).apply{
            // получаем данные из нашей БД
            adapter = HistoryAdapter(LocalRepositoryImpl(App.getHistoryDao()).getAllHistory()).also {
                it.notifyDataSetChanged()
            }
        }
    }
}