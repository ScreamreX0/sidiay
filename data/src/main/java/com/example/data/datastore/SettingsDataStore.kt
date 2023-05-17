package com.example.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.repositories.ISettingsDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsDataStore(private val context: Context) : ISettingsDataStore {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("Settings")
        private val MODE = stringPreferencesKey("mode")
        private val LAST_AUTHORIZED_USER = stringPreferencesKey("last_authorized_user")
    }

    // 0 - Light mode; 1 - Dark mode
    override val getMode: Flow<String?> = context.dataStore.data.map { it[MODE] }
    override suspend fun saveMode(darkMode: Boolean) {
        context.dataStore.edit { it[MODE] = darkMode.toString() }
    }

    override val getLastAuthorizedUser: Flow<UserEntity?> = context.dataStore.data.map {
        Gson().fromJson(
            it[LAST_AUTHORIZED_USER] ?: "",
            (object : TypeToken<UserEntity>() {}).type
        )
    }

    override suspend fun saveLastAuthorizedUser(user: UserEntity) {
        context.dataStore.edit { it[LAST_AUTHORIZED_USER] = Gson().toJson(user) }
    }
}