package com.example.domain.repositories

import com.example.domain.models.entities.Application


interface IApplicationRepository {
    fun getApplicationsListOffline(): List<Application>
    suspend fun getApplicationsList(): List<Application>

    fun getApplicationOffline(id: Int): Application
    suspend fun getApplication(id: Int): Application

    fun changeApplicationOffline(newApplication: Application): Boolean
    suspend fun changeApplication(newApplication: Application): Boolean
}