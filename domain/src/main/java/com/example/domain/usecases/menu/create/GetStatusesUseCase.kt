package com.example.domain.usecases.menu.create

import com.example.domain.enums.TicketsStatuses
import javax.inject.Inject

class GetStatusesUseCase @Inject constructor() {
    fun execute(): List<TicketsStatuses> {
        return TicketsStatuses.values().toList()
    }
}