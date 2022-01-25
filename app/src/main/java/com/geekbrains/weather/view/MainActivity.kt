package com.geekbrains.weather.view

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.geekbrains.weather.R
import com.geekbrains.weather.databinding.ActivityMainBinding
import com.geekbrains.weather.model.MainBroadcastReceiver
import com.geekbrains.weather.model.MainWorker
import com.geekbrains.weather.view.main.MainFragment

// относится к View так как отвечает за отображение
class MainActivity : AppCompatActivity() {
    private val receiver = MainBroadcastReceiver()
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // созданый ресивер регистрируем через метод registerReceiver
        registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        supportFragmentManager.beginTransaction()
            .add(R.id.main_container, MainFragment.newInstance())
            .commit()
//        MainWorker.startWorker(this)
    }

    override fun onDestroy() {
        // снимаем подписку
        unregisterReceiver(receiver)
        super.onDestroy()
    }
}
