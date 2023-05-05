package com.example.domain.usecases.tickets

import com.example.core.utils.ConstAndVars
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
    private val ticketDataStore: ITicketsDataStore
) {
    suspend fun execute(url: String?, currentUserId: Long?, ticket: TicketEntity): Pair<INetworkState?, TicketEntity?> {
        if (ConstAndVars.APPLICATION_MODE == ApplicationModes.DEBUG_AND_OFFLINE) {
            Logger.m(ticket.toString())
            return Pair(null, TicketEntity())
        }

        if (url == null || currentUserId == null) {
            ticketDataStore.saveTickets(ticketDataStore.getTickets.first().plus(ticket))
            return Pair(NetworkState.DONE, null)
        }

        checkConnectionUseCase.execute(url).let {
            if (it == NetworkState.NO_SERVER_CONNECTION) return Pair(it, null)
        }

        val result = ticketRepository.update(url = url, ticket = ticket, currentUserId = currentUserId)
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