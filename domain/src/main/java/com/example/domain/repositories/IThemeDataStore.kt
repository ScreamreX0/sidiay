package com.example.domain.repositories

import kotlinx.coroutines.flow.Flow

interface IThemeDataStore {
    val getMode: Flow<String?>
    suspend fun saveMode(darkMode: Boolean)
}