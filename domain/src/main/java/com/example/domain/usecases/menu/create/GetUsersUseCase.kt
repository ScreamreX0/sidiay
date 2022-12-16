package com.example.domain.usecases.menu.create

import com.example.domain.models.entities.User
import com.example.domain.repositories.IUserRepository
import com.example.domain.utils.Constants
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val userRepository: IUserRepository
) {
    suspend fun execute(): List<User> {
        if (Constants.DEBUG_MODE) {
            return userRepository.getTestUsers()
        }
        return userRepository.getUsers()
    }
}