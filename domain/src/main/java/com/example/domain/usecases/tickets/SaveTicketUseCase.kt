package com.example.domain.usecases.tickets

import com.example.core.utils.ApplicationModes
import com.example.core.utils.Constants
import com.example.core.utils.Logger
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.enums.states.INetworkState
import com.example.domain.enums.states.NetworkState
import com.example.domain.enums.states.TicketOperationState
import com.example.domain.repositories.ITicketsRepository
import com.example.domain.usecases.connections.CheckConnectionUseCase
import javax.inject.Inject

class SaveTicketUseCase @Inject constructor(
    private val ticketRepository: ITicketsRepository,
    private val checkConnectionUseCase: CheckConnectionUseCase
) {
    suspend fun execute(url: String?, ticket: TicketEntity): Pair<INetworkState?, TicketEntity?> {
        if (Constants.APPLICATION_MODE == ApplicationModes.OFFLINE) return Pair(null, TicketEntity())

        url ?: run { return Pair(NetworkState.NO_SERVER_CONNECTION, null) }

        checkConnectionUseCase.execute(url).let {
            if (it == NetworkState.NO_SERVER_CONNECTION) {
                return Pair(it, null)
            }
        }

        Logger.m("Saving ticket..")
        val result = ticketRepository.add(url, ticket)

        return when (result.first) {
            200 -> Pair(null, result.second)
            else -> {
                Logger.m("Error ${result.first}")
                Pair(TicketOperationState.ERROR, null)
            }
        }
    }
}