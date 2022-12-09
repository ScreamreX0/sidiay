package com.example.data.repositories

import com.example.data.api.ApiService
import com.example.domain.models.entities.User
import com.example.domain.enums.NetworkStatuses
import com.example.domain.models.SignInParams
import com.example.domain.repositories.IUserRepository
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService
) : IUserRepository {
    private val unhandledCode = "Unhandled code"

    override suspend fun emailSignIn(authParams: SignInParams): User? {
        val response = apiService.emailIn(authParams.createBody())

        return when (response.code()) {
            NetworkStatuses.OK.code -> response.body()
            NetworkStatuses.BadRequest.code -> null
            else -> throw java.lang.RuntimeException(unhandledCode)
        }
    }

    override fun getEmptyUser(): User {
        return User()
    }
}