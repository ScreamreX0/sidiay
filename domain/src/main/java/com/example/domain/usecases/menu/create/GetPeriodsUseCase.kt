package com.example.domain.usecases.menu.create

import com.example.domain.enums.ticketstates.PeriodState
import javax.inject.Inject

class GetPeriodsUseCase @Inject constructor() {
    fun execute(): List<PeriodState> {
        return PeriodState.values().toList()
    }
}