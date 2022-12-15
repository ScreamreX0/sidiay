package com.example.domain.repositories

import com.example.domain.models.params.SignInParams
import com.example.domain.models.entities.User

interface IUserRepository {
    suspend fun emailSignIn(authParams: SignInParams): User?
    fun getEmptyUser(): User
}