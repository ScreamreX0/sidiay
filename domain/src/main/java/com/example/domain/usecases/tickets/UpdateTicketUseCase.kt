package com.example.domain.usecases.tickets

import com.example.core.utils.ConstAndVars
import com.example.core.utils.ApplicationModes
import com.example.core.utils.Logger
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.enums.states.NetworkConnectionState
import com.example.domain.repositories.ITicketsRepository
import com.example.domain.usecases.connections.CheckConnectionUseCase
import javax.inject.Inject

class UpdateTicketUseCase @Inject constructor(
    private val ticketRepository: ITicketsRepository,
    private val checkConnectionUseCase: CheckConnectionUseCase,
) {
    suspend fun execute(url: String?, currentUserId: Long?, ticket: TicketEntity): Pair<String?, TicketEntity?> {
        if (ConstAndVars.APPLICATION_MODE == ApplicationModes.DEBUG_AND_OFFLINE) {
            Logger.m(ticket.toString())
            return Pair(null, TicketEntity())
        }

        checkConnectionUseCase.execute(url).let {
            if (it == NetworkConnectionState.NO_SERVER_CONNECTION || it == NetworkConnectionState.NO_NETWORK_CONNECTION) {
                return Pair(it.title, null)
            }
        }

        if (url == null || currentUserId == null) { TODO("Offline mode") }

        val result = ticketRepository.update(url = url, ticket = ticket, currentUserId = currentUserId)
        return when (result.first) {
            200 -> Pair(null, result.second)
            422 -> Pair("Заполните все поля помеченные звездой", null)
            else -> {
                Logger.m("Error ${result.first}")
                Pair("Ошибка сохранения", null)
            }
        }
    }
}