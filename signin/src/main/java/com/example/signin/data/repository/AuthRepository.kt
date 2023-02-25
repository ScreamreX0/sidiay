package com.example.signin.data.repository

import com.example.core.ui.utils.Debugger
import com.example.data.api.ApiService
import java.net.ConnectException
import java.util.*
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun checkConnection(url: String): Int {
        val checkResult = apiService.checkConnection(url)
        return checkResult.code()
    }
}