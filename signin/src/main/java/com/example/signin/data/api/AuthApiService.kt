package com.example.signin.data.api

import retrofit2.Response
import retrofit2.http.GET

interface AuthApiService {
    @GET("/")
    suspend fun checkConnection(): Response<String>
}