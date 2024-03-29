package com.example.data.repositories

import com.example.core.utils.Endpoints
import com.example.core.utils.Logger
import com.example.data.network.api.ApiService
import com.example.domain.data_classes.entities.*
import com.example.domain.enums.PrioritiesEnum
import com.example.domain.enums.ServicesEnum
import com.example.domain.enums.TicketStatuses
import com.example.domain.repositories.ITicketsRepository
import javax.inject.Inject

class TicketsRepository @Inject constructor(
    private val apiService: ApiService
) : ITicketsRepository {
    override suspend fun get(url: String, userId: Long): Pair<Int, List<TicketEntity>?> {
        Logger.m("Getting tickets: $url${Endpoints.Tickets.GET_BY_ID}/$userId")
        val result = apiService.getTickets("$url${Endpoints.Tickets.GET_BY_ID}/$userId")

        Logger.m("Getting tickets success. Result code ${result.code()}")

        return Pair(result.code(), result.body())
    }

    override suspend fun get() = List(10) {
        TicketEntity(
            id = it.toLong(),
            executors = listOf(UserEntity(id = 0), UserEntity(id = 1)),
            author = UserEntity(id = 0),
            status = TicketStatuses.values().random().value,
            priority = PrioritiesEnum.values().random().value,
            service = ServicesEnum.values().random().value
        )
    }

    override suspend fun update(
        url: String,
        ticket: TicketEntity,
        currentUserId: Long
    ): Pair<Int, TicketEntity?> {
        Logger.Companion.m("Sending update ticket request: $ticket")
        val result = apiService.updateTicket("$url${Endpoints.Tickets.UPDATE}/$currentUserId", ticket)
        Logger.Companion.m("Update ticket request was sent")
        return Pair(result.code(), result.body())
    }

    override suspend fun add(url: String, ticket: TicketEntity): Pair<Int, TicketEntity?> {
        Logger.Companion.m("Sending add ticket request")
        val result = apiService.addTicket("$url${Endpoints.Tickets.ADD}", ticket)
        Logger.Companion.m("Add ticket request was sent. result code: ${result.code()}")
        return Pair(result.code(), result.body())
    }

    override suspend fun add() = TicketEntity(id = 0)
    override suspend fun subscribe(url: String): Pair<Int, UserEntity?> {
        Logger.m("Sending subscribing ticket request")
        val result = apiService.subscribeToTicket(url)
        Logger.m("Subscribing request was sent")
        return Pair(result.code(), result.body())
    }

    override suspend fun unsubscribe(url: String): Pair<Int, UserEntity?> {
        Logger.m("Sending unsubscribing ticket request")
        val result = apiService.unsubscribeFromTicket(url)
        Logger.m("Unsubscribing request was sent")
        return Pair(result.code(), result.body())
    }

    override suspend fun getSubscriptions(url: String): Pair<Int, List<TicketEntity>?> {
        Logger.m("Sending get subscriptions request")
        val result = apiService.getSubscriptions(url)
        Logger.m("Get subscriptions request was sent")
        return Pair(result.code(), result.body())
    }
}