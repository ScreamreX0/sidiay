package com.example.domain.usecases.menu.create

import com.example.domain.enums.ticket.TicketStatusEnum
import javax.inject.Inject

class GetStatusesUseCase @Inject constructor() {
    fun execute(): List<TicketStatusEnum> {
        return TicketStatusEnum.values().toList()
    }
}