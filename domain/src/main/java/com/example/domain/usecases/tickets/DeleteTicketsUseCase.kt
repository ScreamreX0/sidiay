package com.example.domain.usecases.tickets

import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.repositories.ITicketsRepository
import com.example.core.utils.Constants
import com.example.core.utils.ApplicationModes
import com.example.core.utils.Logger
import com.example.domain.enums.states.INetworkState
import com.example.domain.enums.states.NetworkState
import com.example.domain.repositories.ITicketsDataStore
import com.example.domain.usecases.connections.CheckConnectionUseCase
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DeleteTicketsUseCase @Inject constructor(private val ticketDataStore: ITicketsDataStore, ) {
    suspend fun execute(ticket: TicketEntity) {
        val tickets = ticketDataStore.getTickets.first()?.toList() ?: return
        ticketDataStore.saveTickets(tickets.minus(ticket))
    }
}