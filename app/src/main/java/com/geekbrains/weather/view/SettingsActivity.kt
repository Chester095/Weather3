package com.geekbrains.weather.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Switch
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

        // Declare the switch from the layout file
        val btn = findViewById<SwitchCompat>(R.id.switch_button_dark_theme)

        // set the switch to listen on checked change
        btn.setOnCheckedChangeListener { _, isChecked ->
            // if the button is checked, i.e., towards the right or enabled
            // enable dark mode, change the text to disable dark mode
            // else keep the switch text to enable dark mode
            if (btn.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                btn.text = "Disable dark mode"
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                btn.text = "Enable dark mode"
            }
        }
    }
}