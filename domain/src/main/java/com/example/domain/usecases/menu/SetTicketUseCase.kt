package com.example.domain.usecases.menu

import com.example.domain.models.entities.Ticket
import com.example.domain.repositories.ITicketsRepository
import com.example.domain.utils.Constants
import javax.inject.Inject

class SetTicketUseCase @Inject constructor(
    private val ticketRepository: ITicketsRepository
) {
    suspend fun execute(newTicket: Ticket) : Boolean {
        if (Constants.DEBUG_MODE) {
            return ticketRepository.setTest(newTicket = newTicket)
        }
        return ticketRepository.set(newTicket = newTicket)
    }
}