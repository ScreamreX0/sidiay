package com.example.data.api

import com.example.domain.models.entities.FacilityEntity
import com.example.domain.models.entities.TicketEntity
import com.example.domain.models.entities.UserEntity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET
    suspend fun checkConnection(@Url url: String): Response<Void>

    // Users
    @Multipart
    @POST("/users/sign-in")
    suspend fun signIn(@PartMap map: HashMap<String, String>): Response<UserEntity>

    @GET("/users/get")
    suspend fun getUsers(): Response<List<UserEntity>>

    // Tickets
    @GET("/tickets/get")
    suspend fun getTickets(): Response<List<TicketEntity>>

    @Multipart
    @POST("/tickets/add")
    suspend fun addTicket(@PartMap map: HashMap<String, Any>): Response<Boolean>

    // Facilities
    @GET("/facilities/get")
    suspend fun getFacilities(): Response<List<FacilityEntity>>
}