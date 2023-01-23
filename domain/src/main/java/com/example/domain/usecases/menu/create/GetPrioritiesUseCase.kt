package com.example.domain.usecases.menu.create

import com.example.domain.enums.ticketstates.PriorityState
import javax.inject.Inject

class GetPrioritiesUseCase @Inject constructor() {
    fun execute(): List<PriorityState> {
        return PriorityState.values().toList()
    }
}