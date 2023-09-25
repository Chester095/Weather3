package com.geekbrains.weather.view.activities

import android.os.Bundle
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
        actionBar!!.title = getString(R.string.history_request)
        setContentView(R.layout.activity_history)

        findViewById<RecyclerView>(R.id.history_recycle_view).apply {
            // получаем данные из нашей БД
            Thread {
                adapter = HistoryActivityAdapter(LocalRepositoryImpl(App.getHistoryDao()).getAllHistory()).also {
                    it.notifyDataSetChanged()
                }
            }.start()

        }
    }
}