package com.example.domain.usecases.menu

import com.example.domain.models.entities.Application
import com.example.domain.repositories.IApplicationRepository
import com.example.domain.utils.Constants
import javax.inject.Inject

class ChangeApplicationUseCase @Inject constructor(
    private val applicationRepository: IApplicationRepository
) {
    suspend fun execute(newApplication: Application) : Boolean {
        if (Constants.DEBUG_MODE) {
            return applicationRepository.testChangeApplication(newApplication = newApplication)
        }
        return applicationRepository.changeApplication(newApplication = newApplication)
    }
}