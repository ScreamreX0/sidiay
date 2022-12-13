package com.example.domain.repositories

import com.example.domain.models.entities.Application


interface IApplicationRepository {
    suspend fun getApplicationsList(): List<Application>
    suspend fun getApplication(id: Int): Application
    suspend fun changeApplication(newApplication: Application): Boolean
    suspend fun saveApplication(application: Application): Boolean

    // Test
    suspend fun getTestApplicationsList(): List<Application>
    suspend fun getTestApplication(id: Int): Application
    suspend fun testChangeApplication(newApplication: Application): Boolean
    suspend fun saveTestApplication(application: Application): Boolean
}