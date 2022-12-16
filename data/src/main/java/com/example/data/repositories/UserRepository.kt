package com.example.data.repositories

import com.example.data.api.ApiService
import com.example.domain.models.entities.User
import com.example.domain.enums.states.NetworkStates
import com.example.domain.models.entities.Employee
import com.example.domain.models.params.SignInParams
import com.example.domain.repositories.IUserRepository
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService
) : IUserRepository {
    override suspend fun getTestUsers(): List<User> {
        return List(15) {
            User(
                id = it,
                firstName = "Ikhsanov$it",
                name = "Ruslan$it",
                lastName = "Lenarovich$it"
            )
        }
    }

    override suspend fun getUsers(): List<User> {
        TODO("Not yet implemented")
    }

    override suspend fun emailSignIn(authParams: SignInParams): User? {
        val response = apiService.emailIn(authParams.createBody())

        return when (response.code()) {
            NetworkStates.OK.code -> response.body()
            NetworkStates.BadRequest.code -> null
            else -> null
        }
    }

    override fun getEmptyUser(): User {
        return User()
    }
}