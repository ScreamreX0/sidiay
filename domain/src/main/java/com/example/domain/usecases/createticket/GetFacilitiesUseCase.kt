package com.example.domain.usecases.createticket

import com.example.domain.repositories.IFacilitiesRepository
import com.example.core.ui.utils.Constants
import com.example.domain.models.entities.FacilityEntity
import javax.inject.Inject

class GetFacilitiesUseCase @Inject constructor(
    private val facilitiesRepository: IFacilitiesRepository
) {
    suspend fun execute(): List<FacilityEntity>? {
        if (com.example.core.ui.utils.Constants.DEBUG_MODE) {
            return facilitiesRepository.getTest()
        }
        return facilitiesRepository.get()
    }
}