package com.example.domain.usecases.tickets

import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.repositories.ITicketsRepository
import com.example.core.utils.ConstAndVars
import com.example.core.utils.ApplicationModes
import com.example.core.utils.Logger
import javax.inject.Inject

class GetTicketsUseCase @Inject constructor(
    private val ticketsRepository: ITicketsRepository
) {
    suspend fun execute(url: String, userId: Long): Pair<List<TicketEntity>?, String?> {
        if (ConstAndVars.APPLICATION_MODE == ApplicationModes.DEBUG_AND_OFFLINE) {
            return Pair(ticketsRepository.get(), null)
        }
        val result = ticketsRepository.get(url, userId)

        return when (result.first) {
            200 -> Pair(result.second, null)
            else -> {
                Logger.m("Error ${result.first}")
                Pair(null, "Упс..")
            }
        }
    }
}

