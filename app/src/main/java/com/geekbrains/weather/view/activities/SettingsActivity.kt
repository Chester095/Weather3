package com.geekbrains.weather.view.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
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
        actionBar!!.title = getString(R.string.settings)
        val btn = findViewById<SwitchCompat>(R.id.switch_button_dark_theme)

        val mSettings: SharedPreferences = getSharedPreferences(getString(R.string.APP_PREFERENCES), Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor = mSettings.edit()
        if (mSettings.contains(getString(R.string.APP_NIGHTMODE))) {
            if (mSettings.getBoolean(getString(R.string.APP_NIGHTMODE), true)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                btn.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                btn.isChecked = false
            }
        }

        btn.setOnCheckedChangeListener { _, isChecked ->
            if (btn.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor.putBoolean(getString(R.string.APP_NIGHTMODE), true)
                    .apply()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor.putBoolean(getString(R.string.APP_NIGHTMODE), false)
                    .apply()
            }
        }

        editor.putBoolean(getString(R.string.APP_NIGHTMODE), true)
    }
}