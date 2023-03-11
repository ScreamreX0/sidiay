package com.example.domain.usecases.home

import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.repositories.ITicketsRepository
import javax.inject.Inject

class SetTicketUseCase @Inject constructor(
    private val ticketRepository: ITicketsRepository
) {
    suspend fun execute(newTicketEntity: TicketEntity) : Boolean {
        if (com.example.core.ui.utils.Constants.DEBUG_MODE) {
            return ticketRepository.updateTest(newTicketEntity = newTicketEntity)
        }
        return ticketRepository.update(newTicketEntity = newTicketEntity)
    }
}