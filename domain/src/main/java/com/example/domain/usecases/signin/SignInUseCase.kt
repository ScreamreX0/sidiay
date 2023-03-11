package com.example.domain.usecases.signin

import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.Credentials
import com.example.domain.repositories.IAuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: IAuthRepository
) {
    suspend fun execute(url: String, credentials: Credentials): Pair<Int, UserEntity?> {
        return authRepository.signIn(url, credentials)
    }

    fun getEmptyUser(): Any {
        return authRepository.getEmptyUser()
    }
}