package com.geekbrains.weather.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.geekbrains.weather.R
import com.geekbrains.weather.databinding.ActivityMainBinding
import com.geekbrains.weather.view.main.MainFragment

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .add(R.id.main_container, MainFragment.newInstance())
            .commit()

    }
}
