package com.example.domain.usecases.menu.create

import com.example.domain.enums.ServiceState
import javax.inject.Inject

class GetServicesUseCase @Inject constructor() {
    fun execute(): List<ServiceState> {
        return ServiceState.values().toList()
    }
}