package com.example.domain.usecases.menu.create

import com.example.domain.enums.Priorities
import javax.inject.Inject

class GetPrioritiesUseCase @Inject constructor() {
    fun execute(): List<Priorities> {
        return Priorities.values().toList()
    }
}