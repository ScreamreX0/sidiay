package com.example.domain

import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.enums.TicketStatuses
import com.example.domain.usecases.tickets.FilterTicketsListUseCase
import org.junit.Assert
import org.junit.Test

class FilteringUnitTest {
    private val useCase: FilterTicketsListUseCase = FilterTicketsListUseCase()
    private val ticketsList: List<TicketEntity> = List(10) {
        TicketEntity(
            id = it.toLong(),
            author = UserEntity(id = 0),
            status = TicketStatuses.values().random().value,
        )
    }

    @Test
    fun `should return original list if params is null`() {
        Assert.assertSame(useCase.execute(null, null, ticketsList), ticketsList)
    }
}