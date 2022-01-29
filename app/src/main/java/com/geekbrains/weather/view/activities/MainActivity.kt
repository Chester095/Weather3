package com.geekbrains.weather.view.activities

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContentProviderCompat.requireContext
import com.geekbrains.weather.R
import com.geekbrains.weather.databinding.ActivityMainBinding
import com.geekbrains.weather.model.MainBroadcastReceiver
import com.geekbrains.weather.view.fragments.MainFragment

// относится к View так как отвечает за отображение
class MainActivity : AppCompatActivity() {
    private val receiver = MainBroadcastReceiver()
    private val TAG = "!!! MainActivity "
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // подгружаем тему из SharedPreferences
        val APP_PREFERENCES = "mysettings"
        val mSettings: SharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        val APP_NIGHTMODE = "NIGHTMODE"
        val editor: SharedPreferences.Editor = mSettings.edit()
/*        Log.d(TAG,"  APP_NIGHTMODE  " + mSettings.getBoolean(APP_NIGHTMODE,true))
        editor.putBoolean(APP_NIGHTMODE, false)
            .apply()*/

        Log.d(TAG, "  APP_NIGHTMODE  " + mSettings.getBoolean(APP_NIGHTMODE, true))

        if (mSettings.contains(APP_NIGHTMODE)) {
            if (mSettings.getBoolean(APP_NIGHTMODE, true)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        setContentView(binding.root)
        // созданый ресивер регистрируем через метод registerReceiver
        registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        supportFragmentManager.beginTransaction()
            .add(R.id.main_container, MainFragment.newInstance())
            .commit()
//        MainWorker.startWorker(this)
    }

    /*** Чтобы наш активити узнал о существовании меню.
     * Создание меню.
     * Инфлейтор заходит в notes_list_menu, пройдётся по ней
     * и для каждой создаст пункт меню и добавит в menu
     * @param menu
     * @param inflater
     * @return
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.d(TAG, " onCreateOptionsMenu  $menu")
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /***Реакция на нажатие кнопки меню.
     * У нас есть элемент на который нажали. Проверяем тот ли это элемент.
     * И выполняем openNoteScreen.
     * @param item
     * @return
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings) {
            startActivity(Intent(this, SettingsActivity::class.java))
            return true
        } else if (item.itemId == R.id.action_history) {
            startActivity(Intent(this, HistoryActivity::class.java))
            return true
        } else if (item.itemId == R.id.action_history) {
            startActivity(Intent(this, ContactsActivity::class.java))
            return true
        }

        return super.onOptionsItemSelected(item)
    }
/*    *//*** Инициализация Toolbar
     *
     *//*
    private fun initToolbar() {
        Log.d(MainFragment.TAG, " initToolbar made")
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
    }*/




    override fun onDestroy() {
        // снимаем подписку
        unregisterReceiver(receiver)
        super.onDestroy()
    }
}
