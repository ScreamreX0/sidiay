package com.example.domain.usecases.menu.create

import com.example.domain.enums.ticketstates.TicketStatuses
import javax.inject.Inject

class GetStatusesUseCase @Inject constructor() {
    fun execute(): List<TicketStatuses> {
        return TicketStatuses.values().toList()
    }
}