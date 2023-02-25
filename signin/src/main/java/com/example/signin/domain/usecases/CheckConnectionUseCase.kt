package com.example.signin.domain.usecases

import com.example.signin.data.repository.AuthRepository
import javax.inject.Inject

class CheckConnectionUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun execute(url: String): Boolean {
        val checkResult = authRepository.checkConnection(url)
        return checkResult == 200
    }
}