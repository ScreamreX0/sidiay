package com.example.home.ui.common.ticket_restrictions.impl

import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.TicketRestriction
import com.example.domain.enums.TicketFieldsEnum
import com.example.domain.enums.TicketStatuses
import com.example.home.ui.common.ticket_restrictions.ITicketRestriction

class NotFormedTicketRestriction(
    override val status: TicketStatuses = TicketStatuses.NOT_FORMED
) : ITicketRestriction {
    override fun getRestriction(
        ticket: TicketEntity,
        status: TicketStatuses,
        currentUser: UserEntity
    ) = when (currentUser) {
        ticket.executor -> getEmptyRestriction()
        ticket.author -> TicketRestriction(
            allowedFields = listOf(
                TicketFieldsEnum.NAME,
                TicketFieldsEnum.FACILITIES,
                TicketFieldsEnum.SERVICE,
                TicketFieldsEnum.KIND,
                TicketFieldsEnum.PLANE_DATE,
                TicketFieldsEnum.PRIORITY,
                TicketFieldsEnum.EXECUTOR,
            ),
            availableStatuses = emptyList()
        )

        else -> super.getEmptyRestriction()
    }
}