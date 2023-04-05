package com.example.data.repositories

import com.example.core.utils.Logger
import com.example.data.network.api.ApiService
import com.example.domain.data_classes.entities.*
import com.example.domain.repositories.ITicketsRepository
import javax.inject.Inject

class TicketsRepository @Inject constructor(
    private val apiService: ApiService
) : ITicketsRepository {
    override suspend fun get(url: String): Pair<Int, List<TicketEntity>?> {
        Logger.log("Getting tickets from api")
        val result = apiService.getTickets(url)
        Logger.log("Getting tickets success. Result code ${result.code()}")
        return Pair(result.code(), result.body())
    }

    override suspend fun get() =
        List(10) { TicketEntity(id = it.toLong()) } // TODO("Add some ticket")

    override suspend fun update(
        url: String,
        ticket: TicketEntity,
        currentUserId: Long
    ): Pair<Int, TicketEntity?> {
        Logger.Companion.log("Sending add ticket request")
        val result = apiService.updateTicket(url + "/${currentUserId}", ticket)
        Logger.Companion.log("Add ticket request was sent")
        return Pair(result.code(), result.body())
    }

    override suspend fun update() = TicketEntity(id = 0)

    override suspend fun add(url: String, ticket: TicketEntity): Pair<Int, TicketEntity?> {
        Logger.Companion.log("Sending add ticket request")
        val result = apiService.addTicket(url, ticket)
        Logger.Companion.log("Add ticket request was sent")
        return Pair(result.code(), result.body())
    }

    override suspend fun add() = TicketEntity(id = 0)
}