package com.example.domain.usecases.home

import com.example.core.utils.ConstAndVars
import com.example.core.utils.ApplicationModes
import com.example.core.utils.Logger
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
        if (ConstAndVars.DEBUG_MODE == ApplicationModes.DEBUG_AND_OFFLINE) {
            return Pair(TicketEntity(id = 0), null)
        }
        val result = ticketRepository.update(url = url, ticket = ticket, currentUserId)
        return when (result.first) {
            200 -> Pair(result.second, null)
            else -> {
                Logger.m("Error ${result.first}")
                Pair(null, "Упс..")
            }
        }
    }
}