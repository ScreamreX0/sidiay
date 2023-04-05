package com.example.domain.usecases.createticket

import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.enums.states.TicketCreateStates

class CheckTicketUseCase {
    fun execute(ticket: TicketEntity): List<TicketCreateStates> {
        if (ticket.kind == null
            || ticket.service == null
            || ticket.priority == null) {
            return listOf(TicketCreateStates.FILL_ALL_FIELDS_WITH_STAR)
        }
        return listOf(TicketCreateStates.READY_TO_BE_SAVED)
    }
}

