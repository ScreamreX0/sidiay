package com.example.domain.usecases.tickets.restrictions

import com.example.domain.data_classes.params.TicketRestriction
import com.example.domain.enums.TicketFieldsEnum

class GetTicketCreateRestrictionsUseCase {
    fun execute() = TicketRestriction(
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
}