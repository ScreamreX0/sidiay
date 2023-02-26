package com.example.signin.domain.usecases

import com.example.domain.models.entities.UserEntity
import com.example.domain.models.params.Credentials
import com.example.signin.data.repository.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun execute(url: String, credentials: Credentials): Pair<Int, UserEntity?> {
        return authRepository.signIn(url, credentials)
    }

    fun getEmptyUser(): Any {
        return authRepository.getEmptyUser()
    }
}