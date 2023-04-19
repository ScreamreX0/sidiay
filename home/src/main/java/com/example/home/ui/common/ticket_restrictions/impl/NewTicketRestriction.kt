package com.example.home.ui.common.ticket_restrictions.impl

import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.TicketRestriction
import com.example.domain.enums.TicketStatuses
import com.example.home.ui.common.ticket_restrictions.ITicketRestriction

class NewTicketRestriction(override val status: TicketStatuses) : ITicketRestriction {
    override fun getRestriction(
        ticket: TicketEntity,
        status: TicketStatuses,
        currentUser: UserEntity
    ) = when (currentUser) {
        ticket.executor -> TicketRestriction(
            allowedFields = listOf(),
            availableStatuses = emptyList()
        )
        ticket.author -> TicketRestriction(
            allowedFields = listOf(),
            availableStatuses = emptyList()
        )

        else -> super.getEmptyRestriction()
    }
}