package com.example.domain.repositories

import com.example.domain.data_classes.entities.TicketEntity

interface ITicketsHistoryRepository {
    suspend fun getTicketsHistory(url: String): Pair<Int, List<TicketEntity>?>
    suspend fun getTicketsHistory(): List<TicketEntity>
}