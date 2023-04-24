package com.example.domain.usecases.tickets

import com.example.core.utils.ApplicationModes
import com.example.core.utils.ConstAndVars
import com.example.core.utils.Logger
import com.example.domain.data_classes.params.TicketData
import com.example.domain.repositories.ITicketDataRepository
import javax.inject.Inject

class GetTicketDataUseCase @Inject constructor(
    private val ticketDataRepository: ITicketDataRepository,
) {
    suspend fun execute(url: String): Pair<TicketData?, String?> {
        if (ConstAndVars.APPLICATION_MODE == ApplicationModes.DEBUG_AND_OFFLINE) {
            return Pair(ticketDataRepository.getTicketData(), null)
        }
        val result = ticketDataRepository.getTicketData(url = url)

        return when (result.first) {
            200 -> Pair(result.second, null)
            else -> {
                Logger.m("Error ${result.first}")
                Pair(null, "Упс..")
            }
        }
    }
}