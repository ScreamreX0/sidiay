package com.example.domain.usecases.signin

import com.example.domain.repositories.IAuthRepository
import javax.inject.Inject

class CheckConnectionUseCase @Inject constructor(
    private val authRepository: IAuthRepository
) {
    suspend fun execute(url: String): Boolean {
        return authRepository.checkConnection(url)
    }
}