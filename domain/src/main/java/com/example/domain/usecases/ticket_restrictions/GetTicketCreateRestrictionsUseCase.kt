package com.example.domain.usecases.ticket_restrictions

import com.example.domain.data_classes.params.TicketRestriction
import com.example.domain.enums.ui.TicketFieldsEnum

class GetTicketCreateRestrictionsUseCase {
    fun execute() = TicketRestriction(  // TODO("Переделать")
        allowedFields = listOf(TicketFieldsEnum.TICKET_NAME),
        requiredFields = listOf(
            TicketFieldsEnum.FACILITIES,
            TicketFieldsEnum.SERVICE,
            TicketFieldsEnum.KIND,
            TicketFieldsEnum.PRIORITY,
            TicketFieldsEnum.EXECUTORS,
            TicketFieldsEnum.PLANE_DATE
        ),
        availableStatuses = listOf()
    )
}