package com.geekbrains.weather.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import com.geekbrains.weather.R


class SettingsActivity : AppCompatActivity() {
    @SuppressLint("NotifyDataSetChanged", "UseSwitchCompatOrMaterialCode", "CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val actionBar = supportActionBar
        actionBar!!.title = "Настройки"
        Log.d("!!! SettingsActivity", " onCreate")
        val btn = findViewById<SwitchCompat>(R.id.switch_button_dark_theme)

        val APP_PREFERENCES = "mysettings"
        val mSettings: SharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        val APP_NIGHTMODE = "NIGHTMODE"
        val editor: SharedPreferences.Editor = mSettings.edit()
        if (mSettings.contains(APP_NIGHTMODE)) {
            if (mSettings.getBoolean(APP_NIGHTMODE, true)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                btn.isChecked
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }


        btn.setOnCheckedChangeListener { _, isChecked ->
            if (btn.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor.putBoolean(APP_NIGHTMODE, true)
                    .apply()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor.putBoolean(APP_NIGHTMODE, false)
                    .apply()
            }
        }


        editor.putBoolean(APP_NIGHTMODE, true)

    }
}