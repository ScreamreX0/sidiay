package com.example.domain.usecases.tickets

import com.example.core.utils.Constants
import com.example.core.utils.ApplicationModes
import com.example.core.utils.Logger
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.enums.states.INetworkState
import com.example.domain.enums.states.NetworkState
import com.example.domain.enums.states.TicketOperationState
import com.example.domain.repositories.ITicketsDataStore
import com.example.domain.repositories.ITicketsRepository
import com.example.domain.usecases.connections.CheckConnectionUseCase
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UpdateTicketUseCase @Inject constructor(
    private val ticketRepository: ITicketsRepository,
    private val checkConnectionUseCase: CheckConnectionUseCase,
) {
    suspend fun execute(url: String?, currentUserId: Long?, ticket: TicketEntity): Pair<INetworkState?, TicketEntity?> {
        if (Constants.APPLICATION_MODE == ApplicationModes.OFFLINE) {
            Logger.m(ticket.toString())
            return Pair(null, TicketEntity())
        }

        url ?: run { return Pair(NetworkState.NO_SERVER_CONNECTION, null) }
        currentUserId ?: run { return Pair(NetworkState.NO_SERVER_CONNECTION, null) }

        checkConnectionUseCase.execute(url).let {
            if (it == NetworkState.NO_SERVER_CONNECTION) return Pair(it, null)
        }

        val result: Pair<Int?, TicketEntity?> = if (ticket.id == -1L) {
            ticketRepository.add(url = url, ticket = ticket)
        } else {
            ticketRepository.update(url = url, ticket = ticket, currentUserId = currentUserId)
        }

        return when (result.first) {
            200 -> Pair(null, result.second)
            422 -> Pair(TicketOperationState.FILL_ALL_REQUIRED_FIELDS, null)
            else -> {
                Logger.m("Error ${result.first}")
                Pair(TicketOperationState.ERROR, null)
            }
        }
    }
}