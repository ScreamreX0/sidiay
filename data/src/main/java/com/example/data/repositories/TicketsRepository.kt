package com.example.data.repositories

import com.example.core.utils.Endpoints
import com.example.core.utils.Logger
import com.example.data.network.api.ApiService
import com.example.domain.data_classes.entities.*
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
            executor = UserEntity(id = 0),
            author = UserEntity(id = 0),
            status = TicketStatuses.values().random().value
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
        Logger.Companion.m("Add ticket request was sent")
        return Pair(result.code(), result.body())
    }

    override suspend fun add() = TicketEntity(id = 0)
}