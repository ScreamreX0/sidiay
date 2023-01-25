package com.example.domain.usecases.menu.create

import com.example.domain.enums.ticket.TicketPriorityEnum
import javax.inject.Inject

class GetPrioritiesUseCase @Inject constructor() {
    fun execute(): List<TicketPriorityEnum> {
        return TicketPriorityEnum.values().toList()
    }
}