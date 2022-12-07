package com.example.data.repositories

import com.example.data.api.ApiService
import com.example.data.models.User
import com.example.domain.enums.NetworkStatuses
import com.example.domain.models.SignInParams
import com.example.domain.repositories.IUserRepository
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService
) : IUserRepository {
    private val unhandledCode = "Unhandled code"

    override suspend fun emailSignIn(authParams: SignInParams): Any {
        val response = apiService.emailIn(authParams.createBody())

        return when (response.code()) {
            NetworkStatuses.OK.code -> response.body() ?: throw java.lang.RuntimeException(
                "An error occurred during authorization. Response code 200 but response body is empty"
            )
            NetworkStatuses.BadRequest.code -> "Wrong email or password"
            else -> throw java.lang.RuntimeException(unhandledCode)
        }
    }

    override fun getEmptyUser(): Any {
        return User()
    }
}