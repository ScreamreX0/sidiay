package com.example.domain.usecases.home

import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.repositories.ITicketsRepository
import com.example.core.utils.Constants
import javax.inject.Inject

class GetTicketsUseCase @Inject constructor(
    private val ticketsRepository: ITicketsRepository
) {
    suspend fun execute(url: String): Pair<List<TicketEntity>?, String?> {
        if (Constants.DEBUG_MODE) return Pair(ticketsRepository.get(), null)
        val result = ticketsRepository.get(url)

        return when (result.first) {
            200 -> Pair(result.second, null)
            else -> Pair(null, "Error") // TODO("Add handlers")
        }
    }
}

