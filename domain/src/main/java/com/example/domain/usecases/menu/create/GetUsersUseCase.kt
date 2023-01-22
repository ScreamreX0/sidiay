package com.example.domain.usecases.menu.create

import com.example.domain.models.entities.UserEntity
import com.example.domain.repositories.IUserRepository
import com.example.domain.utils.Constants
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val userRepository: IUserRepository
) {
    suspend fun execute(): List<UserEntity> {
        if (Constants.DEBUG_MODE) {
            return userRepository.getTestUsers()
        }
        return userRepository.getUsers()
    }
}