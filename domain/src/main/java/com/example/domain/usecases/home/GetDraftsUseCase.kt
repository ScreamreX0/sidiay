package com.example.domain.usecases.home

import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.repositories.ITicketsRepository
import com.example.core.utils.Constants
import javax.inject.Inject

// TODO ("Переделать под черновики")
class GetDraftsUseCase @Inject constructor(
    private val ticketsRepository: ITicketsRepository
) {
//    suspend fun execute(): Pair<Int, List<TicketEntity>?> {
//        if (Constants.DEBUG_MODE) {
//            return Pair(200, ticketsRepository.get())
//        }
//
//        return ticketsRepository.get()
//    }
}

