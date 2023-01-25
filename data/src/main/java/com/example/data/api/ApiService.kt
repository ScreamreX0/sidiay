package com.example.data.api

import com.example.domain.models.entities.FacilityEntity
import com.example.domain.models.entities.TicketEntity
import com.example.domain.models.entities.UserEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PartMap

interface ApiService {
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