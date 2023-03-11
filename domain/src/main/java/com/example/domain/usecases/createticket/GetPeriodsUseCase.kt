package com.example.domain.usecases.createticket

import com.example.domain.enums.TicketPeriodEnum
import javax.inject.Inject

class GetPeriodsUseCase @Inject constructor() {
    fun execute(): List<TicketPeriodEnum> {
        return TicketPeriodEnum.values().toList()
    }
}