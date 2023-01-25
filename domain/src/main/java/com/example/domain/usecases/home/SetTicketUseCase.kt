package com.example.domain.usecases.home

import com.example.domain.models.entities.TicketEntity
import com.example.domain.repositories.ITicketsRepository
import com.example.domain.utils.Constants
import javax.inject.Inject

class SetTicketUseCase @Inject constructor(
    private val ticketRepository: ITicketsRepository
) {
    suspend fun execute(newTicketEntity: TicketEntity) : Boolean {
        if (Constants.DEBUG_MODE) {
            return ticketRepository.setTest(newTicketEntity = newTicketEntity)
        }
        return ticketRepository.set(newTicketEntity = newTicketEntity)
    }
}