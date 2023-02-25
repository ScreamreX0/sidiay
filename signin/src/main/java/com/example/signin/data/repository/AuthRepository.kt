package com.example.signin.data.repository

import com.example.signin.data.api.AuthApiService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.util.concurrent.TimeUnit

class AuthRepository {
    suspend fun checkConnection(url: String): Pair<Int, String> {
        var result = Pair(400, "")
        try {
            val client = OkHttpClient()
                .newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()

            val api = retrofit.create(AuthApiService::class.java)

            val checkResult = api.checkConnection()
            result = Pair(checkResult.code(), checkResult.message())
        } catch (_: ConnectException) {
        }
        return result
    }
}