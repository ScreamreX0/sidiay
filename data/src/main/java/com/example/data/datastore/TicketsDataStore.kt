package com.example.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.TicketData
import com.example.domain.enums.ui.MainMenuTabEnum
import com.example.domain.repositories.ITicketsDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TicketsDataStore(private val context: Context): ITicketsDataStore {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("Tickets")
        private val DRAFTS = stringPreferencesKey("drafts")
        private val TICKET_DATA = stringPreferencesKey("ticketData")
    }

    override val getDrafts: Flow<Collection<TicketEntity>?> = context.dataStore.data.map {
        Gson().fromJson<List<TicketEntity>>(it[DRAFTS] ?: "", (object : TypeToken<List<TicketEntity>>() {}).type)
    }

    override val getTicketData: Flow<TicketData?> = context.dataStore.data.map {
        Gson().fromJson(it[TICKET_DATA] ?: "", (object : TypeToken<TicketData>() {}).type)
    }

    override suspend fun saveDrafts(drafts: List<TicketEntity>) {
        context.dataStore.edit { it[DRAFTS] = Gson().toJson(drafts) }
    }

    override suspend fun saveTicketData(ticketData: TicketData) {
        context.dataStore.edit { it[TICKET_DATA] = Gson().toJson(ticketData) }
    }
}