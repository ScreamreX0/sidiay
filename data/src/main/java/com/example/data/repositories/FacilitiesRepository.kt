package com.example.data.repositories

import com.example.data.api.ApiService
import com.example.domain.repositories.IFacilitiesRepository
import com.example.domain.models.entities.Object
import javax.inject.Inject

class FacilitiesRepository @Inject constructor(
    private val apiService: ApiService
) : IFacilitiesRepository {
    override suspend fun get(): List<Object> {
        TODO("Not yet implemented")
    }

    override suspend fun getTest(): List<Object> {
        return List(5) {
            Object(
                id = it,
                name = "TestObject$it"
            )
        }
    }
}