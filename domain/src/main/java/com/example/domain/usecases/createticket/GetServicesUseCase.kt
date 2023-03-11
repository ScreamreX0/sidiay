package com.example.domain.usecases.createticket

import com.example.domain.enums.TicketServiceEnum
import javax.inject.Inject

class GetServicesUseCase @Inject constructor() {
    fun execute(): List<TicketServiceEnum> {
        return TicketServiceEnum.values().toList()
    }
}