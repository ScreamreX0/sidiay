package com.example.domain.usecases.tickets

import com.example.core.utils.ApplicationModes
import com.example.core.utils.ConstAndVars
import com.example.core.utils.Logger
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.enums.states.NetworkConnectionState
import com.example.domain.repositories.ITicketsRepository
import com.example.domain.usecases.connections.CheckConnectionUseCase
import javax.inject.Inject

class SaveTicketUseCase @Inject constructor(
    private val ticketRepository: ITicketsRepository,
    private val checkConnectionUseCase: CheckConnectionUseCase,
) {
    suspend fun execute(url: String?, ticket: TicketEntity): Pair<String?, TicketEntity?> {
        if (ConstAndVars.APPLICATION_MODE == ApplicationModes.DEBUG_AND_OFFLINE) return Pair(null, TicketEntity())

        checkConnectionUseCase.execute(url).let {
            if (it == NetworkConnectionState.NO_SERVER_CONNECTION || it == NetworkConnectionState.NO_NETWORK_CONNECTION) {
                return Pair(it.title, null)
            }
        }

        if (url == null) {
            TODO("Offline mode not yet implemented")
        }

        Logger.m("Saving ticket..")
        val result = ticketRepository.add(url, ticket)

        return when (result.first) {
            200 -> Pair(null, result.second)
            else -> {
                Logger.m("Error ${result.first}")
                Pair("Ошибка сохранения", null)
            }
        }
    }
}