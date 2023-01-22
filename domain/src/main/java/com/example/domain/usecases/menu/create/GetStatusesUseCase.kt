package com.example.domain.usecases.menu.create

import com.example.domain.enums.TicketStates
import javax.inject.Inject

class GetStatusesUseCase @Inject constructor() {
    fun execute(): List<TicketStates> {
        return TicketStates.values().toList()
    }
}