package com.example.domain.repositories

import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.entities.UserEntity


interface ITicketsRepository {
    suspend fun get(url: String, userId: Long): Pair<Int, List<TicketEntity>?>
    suspend fun get(): List<TicketEntity>
    suspend fun update(url: String, ticket: TicketEntity, currentUserId: Long): Pair<Int, TicketEntity?>
    suspend fun add(url: String, ticket: TicketEntity): Pair<Int, TicketEntity?>
    suspend fun add(): TicketEntity
    suspend fun subscribe(url: String): Pair<Int, UserEntity?>
    suspend fun unsubscribe(url: String): Pair<Int, UserEntity?>
    suspend fun getSubscriptions(url: String): Pair<Int, List<TicketEntity>?>
}