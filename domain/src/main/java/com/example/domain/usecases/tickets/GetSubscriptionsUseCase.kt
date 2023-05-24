package com.example.domain.usecases.tickets

import com.example.core.utils.ApplicationModes
import com.example.core.utils.Constants
import com.example.core.utils.Endpoints
import com.example.core.utils.Logger
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.enums.states.INetworkState
import com.example.domain.enums.states.NetworkState
import com.example.domain.enums.states.TicketOperationState
import com.example.domain.repositories.ITicketsRepository
import com.example.domain.usecases.connections.CheckConnectionUseCase
import javax.inject.Inject

class GetSubscriptionsUseCase @Inject constructor(
    private val ticketRepository: ITicketsRepository,
    private val checkConnectionUseCase: CheckConnectionUseCase,
) {
    suspend fun execute(url: String?, currentUserId: Long?): Pair<INetworkState?, List<TicketEntity>?> {
        if (Constants.APPLICATION_MODE == ApplicationModes.OFFLINE) {
            return Pair(NetworkState.NO_SERVER_CONNECTION, null)
        }
        if (url == null || currentUserId == null) return Pair(NetworkState.NO_SERVER_CONNECTION, null)

        checkConnectionUseCase.execute(url).let {
            if (it == NetworkState.NO_SERVER_CONNECTION) return Pair(it, null)
        }

        val result = ticketRepository.getSubscriptions(url = "$url${Endpoints.Tickets.GET_SUBS}/$currentUserId")
        return when (result.first) {
            200 -> Pair(null, result.second)
            else -> {
                Logger.m("Error ${result.first}")
                Pair(TicketOperationState.ERROR, null)
            }
        }
    }
}