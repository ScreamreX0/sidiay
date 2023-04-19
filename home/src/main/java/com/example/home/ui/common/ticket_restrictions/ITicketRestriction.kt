package com.example.home.ui.common.ticket_restrictions

import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.TicketRestriction
import com.example.domain.enums.TicketStatuses

internal interface ITicketRestriction {
    val status: TicketStatuses
    fun getRestriction(
        ticket: TicketEntity,
        status: TicketStatuses,
        currentUser: UserEntity
    ): TicketRestriction
    fun getEmptyRestriction() = TicketRestriction()
}