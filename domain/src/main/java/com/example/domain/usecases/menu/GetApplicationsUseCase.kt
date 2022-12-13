package com.example.domain.usecases.menu

import com.example.domain.models.entities.Application
import com.example.domain.repositories.IApplicationRepository
import com.example.domain.utils.Constants
import javax.inject.Inject

class GetApplicationsUseCase @Inject constructor(
    private val applicationRepository: IApplicationRepository
) {
    suspend fun execute(): List<Application> {
        if (Constants.DEBUG_MODE) {
            return applicationRepository.getTestApplicationsList()
        }
        return applicationRepository.getApplicationsList()
    }
}