package com.example.domain

import com.example.domain.data_classes.entities.EmployeeEntity
import com.example.domain.data_classes.entities.FacilityEntity
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.entities.TransportEntity
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.TicketData
import com.example.domain.enums.JobTitlesEnum
import com.example.domain.enums.TicketStatuses
import com.example.domain.usecases.ticket_restrictions.GetTicketDataRestrictionsUseCase
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetTicketDataRestrictionsTest {
    private lateinit var ticketData: TicketData
    private lateinit var useCase: GetTicketDataRestrictionsUseCase

    @Before
    fun setup() {
        ticketData = TicketData(
            users = List(10) { UserEntity(id = it.toLong()) },
            facilities = List(10) { FacilityEntity(id = it.toLong()) },
            transport = List(10) { TransportEntity(id = it.toLong()) }
        )
        useCase = GetTicketDataRestrictionsUseCase()
    }

    @Test
    fun `should return operator's ticket data`() {
        val currentUser = UserEntity(id = 1, employee = EmployeeEntity(id = 1, jobTitle = JobTitlesEnum.OPERATOR.value))
        val ticket = TicketEntity(service = null, status = TicketStatuses.NEW.value)

        // Act
        val result = useCase.execute(
            currentUser = currentUser,
            ticket = ticket,
            ticketData = ticketData
        )

        // Assert
        assertEquals(result, TicketData())
    }
}