package com.example.data.repositories

import com.example.core.utils.Logger
import com.example.data.network.api.ApiService
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.Credentials
import com.example.domain.repositories.IAuthRepository
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService
) : IAuthRepository {
    override suspend fun checkConnection(url: String) = try {
        apiService.checkConnection(url).isSuccessful
    } catch (e: Exception) {
        false
    }

    override suspend fun signIn(url: String, credentials: Credentials): Pair<Int, UserEntity?> {
        Logger.log("Sending sign in request")

        val body = HashMap<String, String>()
        body["email"] = credentials.email
        body["password"] = credentials.password

        val response = apiService.signIn(url, body)
        Logger.log("Sign in request was sent")

        return Pair(response.code(), response.body())
    }

    override fun getEmptyUser(): UserEntity {
        return UserEntity()
    }
}