package com.example.data.api

import com.example.domain.models.entities.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PartMap

interface ApiService {
    @Multipart
    @POST("/users/sign-in")
    suspend fun signIn(@PartMap map: HashMap<String, String>): Response<User>

    @Multipart
    @GET("/tickets/get")
    suspend fun getTickets(): Response<HashMap<String, Any>>
}