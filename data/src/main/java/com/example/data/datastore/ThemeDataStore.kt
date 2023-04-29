package com.example.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.core.utils.ConstAndVars
import com.example.domain.repositories.IConnectionsDataStore
import com.example.domain.repositories.IThemeDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ThemeDataStore(private val context: Context): IThemeDataStore {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("Theme")
        private val MODE = stringPreferencesKey("mode")
    }

    // 0 - Light mode; 1 - Dark mode
    override val getMode: Flow<String?> = context.dataStore.data.map { it[MODE] }
    override suspend fun saveMode(darkMode: Boolean) {
        context.dataStore.edit { it[MODE] = darkMode.toString() }
    }
}