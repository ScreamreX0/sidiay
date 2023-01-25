package com.example.domain.usecases.menu.create

import com.example.domain.enums.ticket.TicketServiceEnum
import javax.inject.Inject

class GetServicesUseCase @Inject constructor() {
    fun execute(): List<TicketServiceEnum> {
        return TicketServiceEnum.values().toList()
    }
}