package com.example.domain.usecases.createticket

import com.example.domain.models.params.AddTicketParams
import com.example.domain.repositories.ITicketsRepository
import com.example.core.ui.utils.Constants
import javax.inject.Inject

class SaveTicketUseCase @Inject constructor(
    private val ticketRepository: ITicketsRepository
) {
    suspend fun execute(ticketEntity: AddTicketParams): Int {
        if (com.example.core.ui.utils.Constants.DEBUG_MODE) {
            return ticketRepository.addTest()
        }
        return ticketRepository.add(ticketEntity)
    }
}