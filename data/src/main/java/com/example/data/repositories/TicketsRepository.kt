package com.example.data.repositories

import com.example.core.utils.Logger
import com.example.data.network.api.ApiService
import com.example.domain.data_classes.entities.*
import com.example.domain.repositories.ITicketsRepository
import javax.inject.Inject

class TicketsRepository @Inject constructor(
    private val apiService: ApiService
) : ITicketsRepository {
    private val getEndpoint = "/tickets/get-by-id"
    private val addEndpoint = "/tickets/add"
    private val updateEndpoint = "/tickets/update"

    override suspend fun get(url: String, userId: Long): Pair<Int, List<TicketEntity>?> {
        Logger.m("Getting tickets from api")
        val result = apiService.getTickets("$url$getEndpoint/$userId")

        Logger.m("Getting tickets success. Result code ${result.code()}")

        return Pair(result.code(), result.body())
    }

    override suspend fun get() = List(10) {
        TicketEntity(
            id = it.toLong(),
            executor = UserEntity(id = it.toLong()),
            author = UserEntity(id = it.toLong() + 1)
        )
    }

    override suspend fun update(
        url: String,
        ticket: TicketEntity,
        currentUserId: Long
    ): Pair<Int, TicketEntity?> {
        Logger.Companion.m("Sending update ticket request")
        val result = apiService.updateTicket("$url$updateEndpoint/$currentUserId", ticket)
        Logger.Companion.m("Update ticket request was sent")
        return Pair(result.code(), result.body())
    }

    override suspend fun add(url: String, ticket: TicketEntity): Pair<Int, TicketEntity?> {
        Logger.Companion.m("Sending add ticket request")
        val result = apiService.addTicket("$url$addEndpoint", ticket)
        Logger.Companion.m("Add ticket request was sent")
        return Pair(result.code(), result.body())
    }

    override suspend fun add() = TicketEntity(id = 0)
}