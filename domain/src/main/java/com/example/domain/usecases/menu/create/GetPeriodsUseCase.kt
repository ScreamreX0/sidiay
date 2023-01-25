package com.example.domain.usecases.menu.create

import com.example.domain.enums.ticket.TicketPeriodEnum
import javax.inject.Inject

class GetPeriodsUseCase @Inject constructor() {
    fun execute(): List<TicketPeriodEnum> {
        return TicketPeriodEnum.values().toList()
    }
}