package com.example.domain.usecases.menu.create

import com.example.domain.repositories.IFacilitiesRepository
import com.example.domain.utils.Constants
import com.example.domain.models.entities.Facility
import javax.inject.Inject

class GetObjectsUseCase @Inject constructor(
    private val objectsRepository: IFacilitiesRepository
) {
    suspend fun execute(): List<Facility> {
        if (Constants.DEBUG_MODE) {
            return objectsRepository.getTest()
        }
        return objectsRepository.get()
    }
}