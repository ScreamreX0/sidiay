package com.example.domain.usecases.menu.create

import com.example.domain.repositories.IFacilitiesRepository
import com.example.domain.utils.Constants
import com.example.domain.models.entities.FacilityEntity
import javax.inject.Inject

class GetFacilitiesUseCase @Inject constructor(
    private val objectsRepository: IFacilitiesRepository
) {
    suspend fun execute(): List<FacilityEntity> {
        if (Constants.DEBUG_MODE) {
            return objectsRepository.getTest()
        }
        return objectsRepository.get()
    }
}