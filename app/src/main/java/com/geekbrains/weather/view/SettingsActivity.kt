package com.geekbrains.weather.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.geekbrains.weather.R

class SettingsActivity : AppCompatActivity() {
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionBar = supportActionBar
        actionBar!!.title = "Настройки"
        Log.d("!!! SettingsActivity", " onCreate")
        setContentView(R.layout.activity_settings)
    }
}