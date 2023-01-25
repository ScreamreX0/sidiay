package com.example.data.repositories

import com.example.data.api.ApiService
import com.example.domain.models.entities.UserEntity
import com.example.domain.models.params.Credentials
import com.example.domain.repositories.IUserRepository
import com.example.domain.utils.Debugger
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService
) : IUserRepository {
    override suspend fun getTestUsers(): List<UserEntity> {
        return List(15) {
            UserEntity(
                id = it.toLong(),
                firstname = "Ikhsanov$it",
                name = "Ruslan$it",
                lastname = "Lenarovich$it"
            )
        }
    }

    override suspend fun getUsers(): List<UserEntity>? {
        return apiService.getUsers().body()
    }

    override suspend fun signInByEmail(credentials: Credentials): Pair<Int, UserEntity?> {
        Debugger.Companion.printInfo("Sending sign in request")
        val body = HashMap<String, String>()
        body["email"] = credentials.email
        body["password"] = credentials.password
        val response = apiService.signIn(body)
        Debugger.Companion.printInfo("Sign in request was sent")

        return Pair(response.code(), response.body())
    }

    override fun getEmptyUser(): UserEntity {
        return UserEntity()
    }
}