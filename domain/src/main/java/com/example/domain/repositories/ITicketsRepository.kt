package com.example.domain.repositories

import com.example.domain.models.entities.Ticket


interface ITicketsRepository {
    suspend fun get(): Pair<Int, List<Ticket>?>
    suspend fun get(id: Int): Ticket
    suspend fun set(newTicket: Ticket): Boolean
    suspend fun add(ticket: Ticket): Boolean

    // Test
    suspend fun getTest(): List<Ticket>
    suspend fun getTest(id: Int): Ticket
    suspend fun setTest(newTicket: Ticket): Boolean
    suspend fun addTest(ticket: Ticket): Boolean
}