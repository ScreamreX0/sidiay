package com.example.domain.usecases.menu.create

import com.example.domain.enums.states.ApplicationStates
import javax.inject.Inject

class GetStatusesUseCase @Inject constructor() {
    fun execute(): List<ApplicationStates> {
        return ApplicationStates.values().toList()
    }
}