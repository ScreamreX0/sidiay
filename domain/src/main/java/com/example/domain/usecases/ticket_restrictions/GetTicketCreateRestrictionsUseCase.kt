package com.example.domain.usecases.ticket_restrictions

import com.example.domain.data_classes.params.TicketRestriction
import com.example.domain.enums.ui.TicketFieldsEnum

class GetTicketCreateRestrictionsUseCase {
    fun execute() = TicketRestriction(
        allowedFields = listOf(),
        requiredFields = listOf(
            TicketFieldsEnum.TICKET_NAME,
            TicketFieldsEnum.DESCRIPTION_OF_WORK,
            TicketFieldsEnum.KIND,
            TicketFieldsEnum.SERVICE,
            TicketFieldsEnum.FACILITIES,
        ),
        availableStatuses = listOf()
    )
}