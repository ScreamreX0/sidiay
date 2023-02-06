package com.example.sidiay

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        TimeZone.setDefault(TimeZone.getTimeZone("GMT+3"))

        // LANGUAGE
        Locale.setDefault(Locale("ru_ru"))

        // Theme
        val sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        if (sharedPreferences.getString("Theme", "") == "Night") {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}