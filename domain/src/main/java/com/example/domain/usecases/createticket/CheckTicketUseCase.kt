package com.example.domain.usecases.createticket

import com.example.domain.enums.states.TicketStates
import com.example.domain.models.params.AddTicketParams

class CheckTicketUseCase {
    fun execute(ticket: AddTicketParams): List<TicketStates> {
        if (ticket.kind == null) {
            return listOf(TicketStates.FILL_ALL_FIELDS_WITH_STAR)
        }
        return listOf(TicketStates.READY_TO_BE_SAVED)
    }
}