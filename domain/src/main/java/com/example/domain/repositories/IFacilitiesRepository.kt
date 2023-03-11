package com.example.domain.repositories

import com.example.domain.data_classes.entities.FacilityEntity

interface IFacilitiesRepository {
    suspend fun get(): List<FacilityEntity>?
    suspend fun getTest(): List<FacilityEntity>
}