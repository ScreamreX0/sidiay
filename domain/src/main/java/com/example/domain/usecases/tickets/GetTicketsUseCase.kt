package com.example.domain.usecases.tickets

import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.repositories.ITicketsRepository
import com.example.core.utils.ConstAndVars
import com.example.core.utils.ApplicationModes
import com.example.core.utils.Logger
import com.example.domain.enums.states.INetworkState
import com.example.domain.enums.states.NetworkState
import com.example.domain.usecases.connections.CheckConnectionUseCase
import javax.inject.Inject

class GetTicketsUseCase @Inject constructor(
    private val ticketsRepository: ITicketsRepository,
    private val checkConnectionUseCase: CheckConnectionUseCase
) {
    suspend fun execute(url: String, userId: Long): Pair<INetworkState?, List<TicketEntity>?> {
        if (ConstAndVars.APPLICATION_MODE == ApplicationModes.DEBUG_AND_OFFLINE) return Pair(null, ticketsRepository.get())

        checkConnectionUseCase.execute(url).let {
            if (it == NetworkState.NO_SERVER_CONNECTION) return Pair(it, null)
        }

        Logger.m("Getting tickets online with userid: $userId")

        ticketsRepository.get(url, userId).let { result ->
            return when (result.first) {
                200 -> Pair(null, result.second)
                else -> {
                    Logger.m("Unhandled http code: ${result.first}")
                    Pair(NetworkState.ERROR, null)
                }
            }
        }
    }
}

