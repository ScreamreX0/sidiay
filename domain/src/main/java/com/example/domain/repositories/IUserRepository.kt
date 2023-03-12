package com.example.domain.repositories

import com.example.domain.data_classes.params.Credentials
import com.example.domain.data_classes.entities.UserEntity

interface IUserRepository {
    suspend fun getTestUsers(): List<UserEntity>
    suspend fun getUsers(): List<UserEntity>
    suspend fun signInByEmail(credentials: Credentials): Pair<Int, UserEntity?>
    fun getEmptyUser(): UserEntity
}