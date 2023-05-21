package com.example.data.repositories

import com.example.core.utils.Endpoints
import com.example.data.network.api.ApiService
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.enums.PrioritiesEnum
import com.example.domain.enums.ServicesEnum
import com.example.domain.enums.TicketStatuses
import com.example.domain.repositories.ITicketsHistoryRepository
import javax.inject.Inject

class TicketsHistoryRepository @Inject constructor(
    private val apiService: ApiService
) : ITicketsHistoryRepository {
    override suspend fun getTicketsHistory(url: String): Pair<Int, List<TicketEntity>?> {
        val result = apiService.getTicketsHistory("${Endpoints.Tickets.GET_HISTORY}$url")
        return Pair(result.code(), result.body())
    }

    override suspend fun getTicketsHistory() = List(100) {
        TicketEntity(
            id = it.toLong(),
            executors = listOf(UserEntity(id = 0), UserEntity(id = 1)),
            author = UserEntity(id = 0),
            status = TicketStatuses.values().random().value,
            priority = PrioritiesEnum.values().random().value,
            service = ServicesEnum.values().random().value
        )
    }
}