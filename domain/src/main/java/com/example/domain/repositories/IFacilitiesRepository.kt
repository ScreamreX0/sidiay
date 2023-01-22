package com.example.domain.repositories

import com.example.domain.models.entities.Object

interface IFacilitiesRepository {
    suspend fun get(): List<Object>
    suspend fun getTest(): List<Object>
}