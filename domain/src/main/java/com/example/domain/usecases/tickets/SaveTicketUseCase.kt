package com.example.domain.usecases.tickets

import com.example.core.utils.Logger
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.repositories.ITicketsRepository
import javax.inject.Inject

class SaveTicketUseCase @Inject constructor(
    private val ticketRepository: ITicketsRepository
) {
    suspend fun execute(url: String?, ticket: TicketEntity): Pair<TicketEntity?, String?> {
        if (url == null) {
            TODO("Offline mode not yet implemented")
        }
        val result = ticketRepository.add(url, ticket)

        return when (result.first) {
            200 -> Pair(result.second, null)
            else -> {
                Logger.m("Error ${result.first}")
                Pair(null, "Упс..")
            }
        }
    }
}