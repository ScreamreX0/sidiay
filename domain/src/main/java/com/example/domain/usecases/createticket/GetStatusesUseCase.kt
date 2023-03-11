package com.example.domain.usecases.createticket

import com.example.domain.enums.TicketStatusEnum
import javax.inject.Inject

class GetStatusesUseCase @Inject constructor() {
    fun execute(): List<TicketStatusEnum> {
        return TicketStatusEnum.values().toList()
    }
}