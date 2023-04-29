package com.example.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.repositories.IConnectionsDataStore
import com.example.domain.repositories.IDraftsDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DraftsDataStore(private val context: Context): IDraftsDataStore {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("Drafts")
        private val DRAFTS = stringPreferencesKey("drafts")
    }

    override val getDrafts: Flow<Collection<TicketEntity>?> = context.dataStore.data.map {
        Gson().fromJson<List<TicketEntity>>(it[DRAFTS] ?: "", (object : TypeToken<List<TicketEntity>>() {}).type)
    }

    override suspend fun saveDrafts(drafts: Collection<TicketEntity>) {
        context.dataStore.edit { it[DRAFTS] = Gson().toJson(drafts) }
    }
}