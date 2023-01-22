package com.example.data.repositories

import com.example.data.api.ApiService
import com.example.domain.repositories.IFacilitiesRepository
import com.example.domain.models.entities.Facility
import javax.inject.Inject

class FacilitiesRepository @Inject constructor(
    private val apiService: ApiService
) : IFacilitiesRepository {
    override suspend fun get(): List<Facility> {
        TODO("Not yet implemented")
    }

    override suspend fun getTest(): List<Facility> {
        return List(5) {
            Facility(
                id = it.toLong(),
                name = "TestObject$it"
            )
        }
    }
}