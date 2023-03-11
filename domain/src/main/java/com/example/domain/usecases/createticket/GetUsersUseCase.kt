package com.example.domain.usecases.createticket

import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.repositories.IUserRepository
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