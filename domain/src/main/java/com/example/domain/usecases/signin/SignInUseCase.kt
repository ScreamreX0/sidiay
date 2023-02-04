package com.example.domain.usecases.signin

import com.example.domain.models.params.Credentials
import com.example.domain.models.entities.UserEntity
import com.example.domain.repositories.IUserRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val userRepository: IUserRepository
) {
    suspend fun execute(credentials: Credentials): Pair<Int, UserEntity?> {
        return userRepository.signInByEmail(credentials)
    }

    fun getEmptyUser(): Any {
        return userRepository.getEmptyUser()
    }
}
