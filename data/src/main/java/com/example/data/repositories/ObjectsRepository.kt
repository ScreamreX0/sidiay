package com.example.data.repositories

import com.example.data.api.ApiService
import com.example.domain.repositories.IObjectsRepository
import javax.inject.Inject

class ObjectsRepository @Inject constructor(
    private val apiService: ApiService
) : IObjectsRepository {
    override suspend fun getObjects(): List<String> {
        TODO("Not yet implemented")
    }

    override suspend fun getTestObjects(): List<String> {
        return List(5) {
            "TestObject$it"
        }
    }
}