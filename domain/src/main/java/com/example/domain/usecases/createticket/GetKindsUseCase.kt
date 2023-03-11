package com.example.domain.usecases.createticket

import com.example.domain.enums.TicketKindEnum
import javax.inject.Inject

class GetKindsUseCase @Inject constructor() {
    fun execute(): List<TicketKindEnum> {
        return TicketKindEnum.values().toList()
    }
}