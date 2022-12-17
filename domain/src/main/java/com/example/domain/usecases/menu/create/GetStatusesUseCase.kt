package com.example.domain.usecases.menu.create

import com.example.domain.enums.ApplicationStatuses
import javax.inject.Inject

class GetStatusesUseCase @Inject constructor() {
    fun execute(): List<ApplicationStatuses> {
        return ApplicationStatuses.values().toList()
    }
}