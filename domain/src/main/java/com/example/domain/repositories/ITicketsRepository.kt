package com.example.domain.repositories

import com.example.domain.models.entities.TicketEntity


interface ITicketsRepository {
    suspend fun get(): Pair<Int, List<TicketEntity>?>
    suspend fun get(id: Int): TicketEntity
    suspend fun set(newTicketEntity: TicketEntity): Boolean
    suspend fun add(ticketEntity: TicketEntity): Boolean

    // Test
    suspend fun getTest(): List<TicketEntity>
    suspend fun getTest(id: Int): TicketEntity
    suspend fun setTest(newTicketEntity: TicketEntity): Boolean
    suspend fun addTest(ticketEntity: TicketEntity): Boolean
}