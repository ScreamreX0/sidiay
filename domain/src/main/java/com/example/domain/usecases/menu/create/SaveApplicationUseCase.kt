package com.example.domain.usecases.menu.create

import com.example.domain.models.entities.Application
import com.example.domain.repositories.IApplicationRepository
import com.example.domain.utils.Constants
import javax.inject.Inject

class SaveApplicationUseCase @Inject constructor(
    private val applicationRepository: IApplicationRepository
) {
    suspend fun execute(application: Application): Boolean {
        if (Constants.DEBUG_MODE) {
            return applicationRepository.saveTestApplication(application = application)
        }
        return applicationRepository.saveApplication(application = application)
    }
}