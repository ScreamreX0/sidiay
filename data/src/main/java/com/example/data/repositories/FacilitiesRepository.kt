package com.example.data.repositories

import com.example.data.api.ApiService
import com.example.domain.repositories.IFacilitiesRepository
import com.example.domain.models.entities.FacilityEntity
import javax.inject.Inject

class FacilitiesRepository @Inject constructor(
    private val apiService: ApiService
) : IFacilitiesRepository {
    override suspend fun get(): List<FacilityEntity>? {
        return apiService.getFacilities().body()
    }

    override suspend fun getTest(): List<FacilityEntity> {
        return List(5) {
            FacilityEntity(
                id = it.toLong(),
                name = "TestObject$it"
            )
        }
    }
}