package com.example.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.domain.data_classes.params.ConnectionParams
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
class ConnectionsDataStore(private val context: Context) {
    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore("Connections")
        val USER_CONNECTIONS = stringPreferencesKey("user_connections")
    }

    val getConnections: Flow<Collection<ConnectionParams>?> = context.dataStore.data
        .map {
            Gson().fromJson<List<ConnectionParams>>(
                it[USER_CONNECTIONS] ?: "",
                (object : TypeToken<List<ConnectionParams>>() {}).type
            )
        }

    suspend fun saveConnections(connections: Collection<ConnectionParams>) =
        context.dataStore.edit { it[USER_CONNECTIONS] = Gson().toJson(connections) }
}