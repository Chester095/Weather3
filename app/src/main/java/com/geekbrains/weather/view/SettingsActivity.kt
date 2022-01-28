package com.geekbrains.weather.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import com.geekbrains.weather.R

class SettingsActivity : AppCompatActivity() {
    @SuppressLint("NotifyDataSetChanged", "UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionBar = supportActionBar
        actionBar!!.title = "Настройки"
        Log.d("!!! SettingsActivity", " onCreate")
        setContentView(R.layout.activity_settings)

        val btn = findViewById<SwitchCompat>(R.id.switch_button_dark_theme)
        btn.setOnCheckedChangeListener { _, isChecked ->
            if (btn.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}