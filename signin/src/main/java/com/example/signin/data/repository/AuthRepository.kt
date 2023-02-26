package com.example.signin.data.repository

import com.example.core.ui.utils.Debugger
import com.example.data.api.ApiService
import com.example.domain.models.entities.UserEntity
import com.example.domain.models.params.Credentials
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun checkConnection(url: String): Int {
        val checkResult = apiService.checkConnection(url)
        return checkResult.code()
    }

    suspend fun signIn(url: String, credentials: Credentials): Pair<Int, UserEntity?> {
        Debugger.printInfo("Sending sign in request")

        val body = HashMap<String, String>()
        body["email"] = credentials.email
        body["password"] = credentials.password

        val response = apiService.signIn(url, body)
        Debugger.printInfo("Sign in request was sent")

        return Pair(response.code(), response.body())
    }

    fun getEmptyUser(): UserEntity {
        return UserEntity()
    }
}