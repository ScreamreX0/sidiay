package com.example.domain.repositories

import com.example.domain.models.entities.FacilityEntity

interface IFacilitiesRepository {
    suspend fun get(): List<FacilityEntity>?
    suspend fun getTest(): List<FacilityEntity>
}