package com.example.domain.usecases.home

import com.example.core.utils.Constants
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.repositories.ITicketsRepository
import javax.inject.Inject

class UpdateTicketUseCase @Inject constructor(
    private val ticketRepository: ITicketsRepository
) {
    suspend fun execute(
        url: String,
        currentUserId: Long,
        ticket: TicketEntity
    ): Pair<TicketEntity?, String?> {
        if (Constants.DEBUG_MODE) return Pair(ticketRepository.update(), null)
        val result = ticketRepository.update(url = url, ticket = ticket, currentUserId)
        return when (result.first) {
            200 -> Pair(result.second, null)
            else -> Pair(null, "Error") // TODO("Add handlers")
        }
    }
}