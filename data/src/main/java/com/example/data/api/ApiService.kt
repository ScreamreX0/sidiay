package com.example.data.api

import com.example.data.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/users/email-in")
    suspend fun emailIn(@Body body: HashMap<String, Any>): Response<User>
}