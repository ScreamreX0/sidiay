package com.example.domain

import com.example.domain.data_classes.params.TicketRestriction
import com.example.domain.enums.TicketFieldsEnum
import com.example.domain.usecases.ticket_restrictions.GetTicketCreateRestrictionsUseCase
import org.junit.Assert
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun getTicketCreateRestrictionsUseCase_isCorrect() {
        Assert.assertEquals(
            GetTicketCreateRestrictionsUseCase().execute(),
            TicketRestriction(
                allowedFields = listOf(TicketFieldsEnum.NAME),
                requiredFields = listOf(
                    TicketFieldsEnum.FACILITIES,
                    TicketFieldsEnum.SERVICE,
                    TicketFieldsEnum.KIND,
                    TicketFieldsEnum.PRIORITY,
                    TicketFieldsEnum.EXECUTOR,
                    TicketFieldsEnum.PLANE_DATE
                ),
                availableStatuses = listOf()
            )
        )
    }
}