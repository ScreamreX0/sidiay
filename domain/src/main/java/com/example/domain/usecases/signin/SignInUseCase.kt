package com.example.domain.usecases.signin

import com.example.domain.models.SignInParams
import com.example.domain.repositories.IUserRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val userRepository: IUserRepository
) {
    suspend fun execute(params: SignInParams): Any {
        return userRepository.emailSignIn(params)
    }
}