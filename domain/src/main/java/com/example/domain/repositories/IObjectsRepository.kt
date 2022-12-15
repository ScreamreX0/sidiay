package com.example.domain.repositories

interface IObjectsRepository {
    suspend fun getObjects(): List<String>
    suspend fun getTestObjects(): List<String>
}