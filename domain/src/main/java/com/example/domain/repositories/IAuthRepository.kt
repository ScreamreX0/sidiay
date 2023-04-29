package com.example.domain.repositories

import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.Credentials

interface IAuthRepository {
    suspend fun signIn(url: String, credentials: Credentials): Pair<Int, UserEntity?>
}