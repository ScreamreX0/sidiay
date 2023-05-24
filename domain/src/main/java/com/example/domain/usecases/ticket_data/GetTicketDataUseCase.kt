package com.example.domain.usecases.ticket_data

import com.example.core.utils.ApplicationModes
import com.example.core.utils.Constants
import com.example.core.utils.Endpoints
import com.example.core.utils.Logger
import com.example.domain.data_classes.params.TicketData
import com.example.domain.enums.states.INetworkState
import com.example.domain.enums.states.NetworkState
import com.example.domain.repositories.ITicketDataRepository
import com.example.domain.repositories.ITicketsDataStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetTicketDataUseCase @Inject constructor(
    private val ticketDataRepository: ITicketDataRepository,
    private val ticketDataStore: ITicketsDataStore,
    private val saveTicketDataUseCase: SaveTicketDataUseCase,
) {
    suspend fun execute(url: String?, currentUserId: Long?): Pair<INetworkState?, TicketData?> {
        if (Constants.APPLICATION_MODE == ApplicationModes.OFFLINE) {
            return Pair(null, ticketDataRepository.getTicketData())
        }

        if (url == null || currentUserId == null) {
            Logger.m("Getting ticket data offline...")
            return Pair(NetworkState.DONE, ticketDataStore.getTicketData.first())
        }

        Logger.m("Getting ticket data online...")
        val result = ticketDataRepository.getTicketData(url = "$url${Endpoints.Tickets.GET_DATA}/$currentUserId")

        return when (result.first) {
            200 -> {
                saveTicketDataUseCase.execute(result.second ?: TicketData())
                Pair(null, result.second)
            }

            else -> {
                Logger.m("Unresolved error while getting ticket data. Status code: ${result.first}")
                Pair(NetworkState.ERROR, null)
            }
        }
    }
}
    