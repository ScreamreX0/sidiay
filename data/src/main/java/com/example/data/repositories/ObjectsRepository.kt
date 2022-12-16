package com.example.data.repositories

import com.example.data.api.ApiService
import com.example.domain.repositories.IObjectsRepository
import com.example.domain.models.entities.Object
import javax.inject.Inject

class ObjectsRepository @Inject constructor(
    private val apiService: ApiService
) : IObjectsRepository {
    override suspend fun getObjects(): List<Object> {
        TODO("Not yet implemented")
    }

    override suspend fun getTestObjects(): List<Object> {
        return List(5) {
            Object(
                id = it,
                name = "TestObject$it"
            )
        }
    }
}