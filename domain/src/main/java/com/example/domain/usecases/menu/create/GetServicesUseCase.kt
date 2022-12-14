package com.example.domain.usecases.menu.create

import com.example.domain.enums.Services
import javax.inject.Inject

class GetServicesUseCase @Inject constructor() {
    fun execute(): List<Services> {
        return Services.values().toList()
    }
}