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
    private val signInEndpoint = "/users/sign-in"
    override suspend fun checkConnection(url: String) =
        try { apiService.checkConnection("$url/users").isSuccessful } catch (e: Exception) { false }

    override suspend fun signIn(url: String, credentials: Credentials): Pair<Int, UserEntity?> {
        Logger.m("Sending sign in request: $url$signInEndpoint")
        Logger.m("  Credentials: $credentials")

        val response = apiService.signIn("$url$signInEndpoint", credentials)
        Logger.m("Sign in request was sent")

        return Pair(response.code(), response.body())
    }
}