package com.example.domain.repositories

import com.example.domain.models.params.Credentials
import com.example.domain.models.entities.UserEntity

interface IUserRepository {
    suspend fun getTestUsers(): List<UserEntity>
    suspend fun getUsers(): List<UserEntity>
    suspend fun signInByEmail(credentials: Credentials): Pair<Int, UserEntity?>
    fun getEmptyUser(): UserEntity
}