package com.example.domain.usecases.menu

import com.example.domain.models.entities.Ticket
import com.example.domain.repositories.ITicketsRepository
import com.example.domain.utils.Constants
import javax.inject.Inject

class GetTicketsUseCase @Inject constructor(
    private val ticketsRepository: ITicketsRepository
) {
    suspend fun execute(): Pair<Int, List<Ticket>?> {
        if (Constants.DEBUG_MODE) {
            return Pair(200, ticketsRepository.getTest())
        }

        return ticketsRepository.get()
    }
}