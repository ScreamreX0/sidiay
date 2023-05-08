package com.example.domain.usecases.tickets

import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.repositories.ITicketsRepository
import com.example.core.utils.Constants
import com.example.core.utils.ApplicationModes
import com.example.core.utils.Logger
import com.example.domain.enums.states.INetworkState
import com.example.domain.enums.states.NetworkState
import com.example.domain.repositories.ITicketsDataStore
import com.example.domain.usecases.connections.CheckConnectionUseCase
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetTicketsUseCase @Inject constructor(
    private val ticketsRepository: ITicketsRepository,
    private val checkConnectionUseCase: CheckConnectionUseCase,
    private val ticketDataStore: ITicketsDataStore,
) {
    suspend fun execute(url: String?, userId: Long): Pair<INetworkState?, List<TicketEntity>?> {
        if (Constants.APPLICATION_MODE == ApplicationModes.OFFLINE) return Pair(null, ticketsRepository.get())

        if (url == null) {
            Logger.m("Getting tickets offline")
            return Pair(NetworkState.DONE, ticketDataStore.getTickets.first()?.toList())
        }

        checkConnectionUseCase.execute(url).let {
            if (it == NetworkState.NO_SERVER_CONNECTION) return Pair(it, null)
        }

        Logger.m("Getting tickets online with userid: $userId")

        ticketsRepository.get(url, userId).let { result ->
            return when (result.first) {
                200 -> {
                    ticketDataStore.saveTickets(result.second?.toList() ?: listOf())
                    Pair(null, result.second)
                }
                else -> {
                    Logger.m("Unhandled http code: 