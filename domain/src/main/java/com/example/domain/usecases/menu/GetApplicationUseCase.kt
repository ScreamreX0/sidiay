package com.example.domain.usecases.menu

import com.example.domain.models.entities.Application
import com.example.domain.repositories.IApplicationRepository
import com.example.domain.utils.Constants
import javax.inject.Inject

class GetApplicationUseCase @Inject constructor(
    private val applicationRepository: IApplicationRepository
) {
    suspend fun execute(id: Int): Application {
        if (Constants.DEBUG_MODE) {
            return applicationRepository.getApplication(id = id)
        }
        return applicationRepository.getTestApplication(id = id)
    }
}