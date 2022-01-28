package com.geekbrains.weather.view.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.geekbrains.weather.R

class SettingsFragment : Fragment() {
    companion object {
        private const val TAG = "!!! SettingsFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "SettingsFragment   onCreateView.   inflater = $inflater")
        Log.d(TAG, "SettingsFragment   onCreateView.   container = $container")
        Log.d(TAG, "SettingsFragment   onCreateView.   savedInstanceState = $savedInstanceState")
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }
}