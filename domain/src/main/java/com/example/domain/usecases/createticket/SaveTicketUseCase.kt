package com.example.domain.usecases.createticket

import com.example.core.utils.Constants
import com.example.domain.data_classes.params.CreateTicketParams
import com.example.domain.repositories.ITicketsRepository
import javax.inject.Inject

class SaveTicketUseCase @Inject constructor(
    private val ticketRepository: ITicketsRepository
) {
    suspend fun execute(ticketEntity: CreateTicketParams): Int {
        if (Constants.DEBUG_MODE) {
            return ticketRepository.addTest()
        }
        return ticketRepository.add(ticketEntity)
    }
}