package com.example.domain.repositories

import com.example.domain.data_classes.entities.TicketEntity


interface ITicketsRepository {
    suspend fun get(url: String, userId: Long): Pair<Int, List<TicketEntity>?>
    suspend fun get(): List<TicketEntity>
    suspend fun update(url: String, ticket: TicketEntity, currentUserId: Long): Pair<Int, TicketEntity?>
    suspend fun add(url: String, ticket: TicketEntity): Pair<Int, TicketEntity?>
    suspend fun add(): TicketEntity
}