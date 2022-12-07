package com.example.domain.repositories

import com.example.domain.models.SignInParams

interface IUserRepository {
    suspend fun emailSignIn(authParams: SignInParams): Any
    fun getEmptyUser(): Any
}