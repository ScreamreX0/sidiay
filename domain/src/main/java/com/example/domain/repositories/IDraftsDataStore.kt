package com.example.domain.repositories

import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.ConnectionParams
import kotlinx.coroutines.flow.Flow

interface IDraftsDataStore {
    val getDrafts: Flow<Collection<TicketEntity>?>
    suspend fun saveDrafts(drafts: Collection<TicketEntity>)
}