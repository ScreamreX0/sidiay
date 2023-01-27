package com.example.sidiay.presentation.activity

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.sidiay.R
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
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