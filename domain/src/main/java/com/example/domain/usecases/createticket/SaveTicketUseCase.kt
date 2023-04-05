package com.example.domain.usecases.createticket

import com.example.core.utils.Constants
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.repositories.ITicketsRepository
import javax.inject.Inject

class SaveTicketUseCase @Inject constructor(
    private val ticketRepository: ITicketsRepository
) {
    suspend fun execute(url: String, ticket: TicketEntity): Pair<TicketEntity?, String?> {
        if (Constants.DEBUG_MODE) {
            return Pair(ticketRepository.add(), null)
        }
        val result = ticketRepository.add(url, ticket)

        return when (result.first) {
            200 -> Pair(result.second, null)
            else -> Pair(null, "Error") // TODO("Add handlers")
        }
    }
}