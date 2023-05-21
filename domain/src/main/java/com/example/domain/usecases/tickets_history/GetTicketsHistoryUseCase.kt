package com.example.domain.usecases.tickets_history

import com.example.core.utils.ApplicationModes
import com.example.core.utils.Constants
import com.example.core.utils.Logger
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.TicketData
import com.example.domain.enums.states.INetworkState
import com.example.domain.enums.states.NetworkState
import com.example.domain.repositories.ITicketDataRepository
import com.example.domain.repositories.ITicketsDataStore
import com.example.domain.repositories.ITicketsHistoryRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetTicketsHistoryUseCase @Inject constructor(
    private val ticketsHistoryRepository: ITicketsHistoryRepository,
) {
    suspend fun execute(url: String?, currentUserId: Long?): Pair<INetworkState?, List<TicketEntity>?> {
        if (Constants.APPLICATION_MODE == ApplicationModes.OFFLINE) {
            return Pair(null, ticketsHistoryRepository.getTicketsHistory())
        }

        if (url == null || currentUserId == null) { return Pair(NetworkState.NO_SERVER_CONNECTION, null) }

        val result = ticketsHistoryRepository.getTicketsHistory("$url/$currentUserId")

        return when (result.first) {
            200 -> {
                Pair(null, result.second)
            }

            else -> {
                Logger.m("Unresolved error while getting ticket data. Status code: ${result.first}")
                Pair(NetworkState.ERROR, null)
            }
        }
    }
}
    