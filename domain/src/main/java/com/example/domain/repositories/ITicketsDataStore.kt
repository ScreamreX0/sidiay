package com.example.domain.repositories

import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.TicketData
import kotlinx.coroutines.flow.Flow

interface ITicketsDataStore {
    // Tickets
    val getTickets: Flow<Collection<TicketEntity>?>
    suspend fun saveTickets(tickets: Collection<TicketEntity>)

    // Drafts
    val getDrafts: Flow<Collection<TicketEntity>?>
    suspend fun saveDrafts(drafts: Collection<TicketEntity>)

    // Ticket data
    val getTicketData: Flow<TicketData?>
    suspend fun saveTicketData(ticketData: TicketData)
}