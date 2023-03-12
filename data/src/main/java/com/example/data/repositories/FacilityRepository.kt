package com.example.data.repositories

import com.example.data.network.api.ApiService
import com.example.domain.repositories.IFacilityRepository
import com.example.domain.data_classes.entities.FacilityEntity
import javax.inject.Inject

class FacilityRepository @Inject constructor(
    private val apiService: ApiService
) : IFacilityRepository {
    override suspend fun get(): List<FacilityEntity> {
        return apiService.getFacilities().body() ?: emptyList()
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