package com.example.domain.usecases.createticket

import com.example.domain.enums.states.TicketCreateStates
import com.example.domain.data_classes.params.AddTicketParams

class CheckTicketUseCase {
    fun execute(ticket: AddTicketParams): List<TicketCreateStates> {
        if (ticket.kind == null
            || ticket.service == null
            || ticket.priority == null) {
            return listOf(TicketCreateStates.FILL_ALL_FIELDS_WITH_STAR)
        }
        return listOf(TicketCreateStates.READY_TO_BE_SAVED)
    }
}

