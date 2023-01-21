package com.example.domain.repositories

import com.example.domain.models.params.Credentials
import com.example.domain.models.entities.User

interface IUserRepository {
    suspend fun getTestUsers(): List<User>
    suspend fun getUsers(): List<User>
    suspend fun signInByEmail(credentials: Credentials): Pair<Int, User?>
    fun getEmptyUser(): User
}