package com.example.domain.usecases.createticket

import com.example.domain.models.entities.UserEntity
import com.example.domain.repositories.IUserRepository
import com.example.core.ui.utils.Constants
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val userRepository: IUserRepository
) {
    suspend fun execute(): List<UserEntity>? {
        if (com.example.core.ui.utils.Constants.DEBUG_MODE) {
            return userRepository.getTestUsers()
        }
        return userRepository.getUsers()
    }
}