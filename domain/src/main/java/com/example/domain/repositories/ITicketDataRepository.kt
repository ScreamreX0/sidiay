package com.example.domain.repositories

import com.example.domain.data_classes.params.TicketData

interface ITicketDataRepository {
    suspend fun getTicketData(url: String): Pair<Int, TicketData?>
    suspend fun getTicketData(): TicketData
}