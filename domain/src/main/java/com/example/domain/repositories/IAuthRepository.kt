package com.example.domain.repositories

import com.example.domain.models.entities.UserEntity
import com.example.domain.models.params.Credentials

interface IAuthRepository {
    suspend fun checkConnection(url: String): Boolean
    suspend fun signIn(url: String, credentials: Credentials): Pair<Int, UserEntity?>
    fun getEmptyUser(): UserEntity
}