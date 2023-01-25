package com.example.domain.usecases.menu.create

import com.example.domain.models.entities.TicketEntity
import com.example.domain.models.params.AddTicketParams
import com.example.domain.repositories.ITicketsRepository
import com.example.domain.utils.Constants
import javax.inject.Inject

class SaveTicketUseCase @Inject constructor(
    private val ticketRepository: ITicketsRepository
) {
    suspend fun execute(ticketEntity: AddTicketParams): Int {
        if (Constants.DEBUG_MODE) {
            return ticketRepository.addTest()
        }
        return ticketRepository.add(ticketEntity)
    }
}