package com.example.domain.usecases.createticket

import com.example.domain.enums.TicketPriorityEnum
import javax.inject.Inject

class GetPrioritiesUseCase @Inject constructor() {
    fun execute(): List<TicketPriorityEnum> {
        return TicketPriorityEnum.values().toList()
    }
}