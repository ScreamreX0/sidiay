package com.example.domain.usecases.menu

import com.example.domain.models.entities.Application
import com.example.domain.repositories.IApplicationRepository
import javax.inject.Inject

class GetApplicationsOfflineUseCase @Inject constructor(
    private val applicationsRepository: IApplicationRepository
) {
    fun execute(): List<Application> {
        return applicationsRepository.getApplicationsListOffline()
    }
}