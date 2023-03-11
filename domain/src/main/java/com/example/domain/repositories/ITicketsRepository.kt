package com.example.domain.repositories

import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.AddTicketParams


interface ITicketsRepository {
    suspend fun get(): Pair<Int, List<TicketEntity>?>
    suspend fun get(id: Int): TicketEntity
    suspend fun get(url: String, start: Int, end: Int): Pair<Int, List<TicketEntity>?>
    suspend fun set(newTicketEntity: TicketEntity): Boolean
    suspend fun add(ticketEntity: AddTicketParams): Int

    // Test
    suspend fun getTest(): List<TicketEntity>
    suspend fun getTest(id: Int): TicketEntity
    suspend fun setTest(newTicketEntity: TicketEntity): Boolean
    suspend fun addTest(): Int
}