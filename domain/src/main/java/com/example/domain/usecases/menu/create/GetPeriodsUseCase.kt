package com.example.domain.usecases.menu.create

import com.example.domain.enums.Period
import javax.inject.Inject

class GetPeriodsUseCase @Inject constructor() {
    fun execute(): List<Period> {
        return Period.values().toList()
    }
}