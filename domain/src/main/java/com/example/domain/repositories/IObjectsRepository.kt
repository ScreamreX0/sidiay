package com.example.domain.repositories

import com.example.domain.models.entities.Object

interface IObjectsRepository {
    suspend fun getObjects(): List<Object>
    suspend fun getTestObjects(): List<Object>
}