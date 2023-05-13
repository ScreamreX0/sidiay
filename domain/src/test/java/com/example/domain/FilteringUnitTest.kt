package com.example.domain

import com.example.domain.data_classes.entities.PriorityEntity
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.FilteringParams
import com.example.domain.enums.TicketStatuses
import com.example.domain.usecases.tickets.FilterTicketsListUseCase
import org.junit.Assert
import org.junit.Test

class FilteringUnitTest {
    private val useCase: FilterTicketsListUseCase = FilterTicketsListUseCase()
    private val ticketsList: List<TicketEntity> = List(10) {
        TicketEntity(
            id = it.toLong(),
            executor = UserEntity(id = 0),
            author = UserEntity(id = 0),
            status = TicketStatuses.values().random().value,
            priority = PriorityEntity(id = it.toLong())
        )
    }

    @Test
    fun `should return original list if params is null`() {
        Assert.assertSame(useCase.execute(null, null, ticketsList), ticketsList)
    }

    @Test
    fun `should filter correctly by priority`() {
        val filterParams = FilteringParams(priority = listOf(PriorityEntity(id = 2), PriorityEntity(id = 5)))
        Assert.assertEquals(null, useCase.execute(filterParams, null, ticketsList))
    }
}