package com.example.data.repositories

import com.example.data.network.api.ApiService
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.Credentials
import com.example.domain.repositories.IUserRepository
import com.example.core.ui.utils.Debugger
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService
) : IUserRepository {
    override suspend fun getUsers(): List<UserEntity>? {
        return apiService.getUsers().body()
    }

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

    override suspend fun signInByEmail(credentials: Credentials): Pair<Int, UserEntity?> {
        Debugger.printInfo("Sending sign in request")

        val body = HashMap<String, String>()
        body["email"] = credentials.email
        body["password"] = credentials.password

        val response = apiService.signIn("", body)
        Debugger.printInfo("Sign in request was sent")

        return Pair(response.code(), response.body())
    }

    override fun getEmptyUser(): UserEntity {
        return UserEntity()
    }
}