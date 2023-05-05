package com.example.domain.usecases.ticket_data

import com.example.domain.data_classes.params.TicketData
import com.example.domain.repositories.ITicketsDataStore
import javax.inject.Inject

class SaveTicketDataUseCase @Inject constructor(
    private val ticketsDataStore: ITicketsDataStore
) {
    suspend fun execute(ticketData: TicketData) = ticketsDataStore.saveTicketData(ticketData)
}