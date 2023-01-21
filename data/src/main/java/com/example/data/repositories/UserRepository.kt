package com.example.data.repositories

import com.example.data.api.ApiService
import com.example.domain.models.entities.User
import com.example.domain.models.params.Credentials
import com.example.domain.repositories.IUserRepository
import com.example.domain.utils.Debugger
import retrofit2.Call
import retrofit2.Response
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

    override suspend fun signInByEmail(credentials: Credentials): Pair<Int, User?> {
        Debugger.Companion.printInfo("Sending sign in request")
        val body = HashMap<String, String>()
        body["email"] = credentials.email
        body["password"] = credentials.password
        val response = apiService.signIn(body)
        Debugger.Companion.printInfo("Sign in request was sent")

        return Pair(response.code(), response.body())
    }

    override fun getEmptyUser(): User {
        return User()
    }
}