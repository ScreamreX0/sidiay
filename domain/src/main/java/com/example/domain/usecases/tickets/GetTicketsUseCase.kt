package com.example.domain.usecases.tickets

import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.repositories.ITicketsRepository
import com.example.core.utils.ConstAndVars
import com.example.core.utils.ApplicationModes
import com.example.core.utils.Logger
import com.example.domain.enums.states.NetworkConnectionState
import com.example.domain.usecases.connections.CheckConnectionUseCase
import javax.inject.Inject

class GetTicketsUseCase @Inject constructor(
    private val ticketsRepository: ITicketsRepository,
    private val checkConnectionUseCase: CheckConnectionUseCase
) {
    suspend fun execute(url: String, userId: Long): Pair<String?, List<TicketEntity>?> {
        if (ConstAndVars.APPLICATION_MODE == ApplicationModes.DEBUG_AND_OFFLINE) return Pair(null, ticketsRepository.get())

        checkConnectionUseCase.execute(url).let {
            if (it == NetworkConnectionState.NO_SERVER_CONNECTION || it == NetworkConnectionState.NO_NETWORK_CONNECTION) {
                return Pair(it.title, null)
            }
        }

        Logger.m("Getting tickets online with userid: $userId")
        val result = ticketsRepository.get(url, userId)

        return when (result.first) {
            200 -> Pair(null, result.second)
            else -> {
                Logger.m("Unhandled http code: ${result.first}")
                Pair("Неизвестная ошибка", null)
            }
        }
    }
}

