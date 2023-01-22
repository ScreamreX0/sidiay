package com.example.domain.repositories

import com.example.domain.models.entities.Facility

interface IFacilitiesRepository {
    suspend fun get(): List<Facility>
    suspend fun getTest(): List<Facility>
}