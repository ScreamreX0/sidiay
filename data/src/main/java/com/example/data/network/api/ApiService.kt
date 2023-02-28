package com.example.data.network.api

import com.example.domain.models.entities.FacilityEntity
import com.example.domain.models.entities.TicketEntity
import com.example.domain.models.entities.UserEntity
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    /** Connections */
    @GET
    suspend fun checkConnection(@Url url: String): Response<Void>

    /** Users */
    //@Multipart
    //@POST("/users/sign-in")
    //suspend fun signIn(@PartMap map: HashMap<String, String>): Response<UserEntity>

    @POST
    @Multipart
    suspend fun signIn(
        @Url url: String,
        @PartMap map: HashMap<String, String>
    ): Response<UserEntity>

    @GET("/users/get")
    suspend fun getUsers(): Response<List<UserEntity>>

    /** Tickets */
    @GET("/tickets/get")
    suspend fun getTickets(): Response<List<TicketEntity>>

    @GET
    @Multipart
    suspend fun getTickets(
        @Url url: String,
    ): Response<List<TicketEntity>>

    @POST("/tickets/add")
    @Multipart
    suspend fun addTicket(@PartMap map: HashMap<String, Any>): Response<Boolean>

    /** Facilities */
    @GET("/facilities/get")
    suspend fun getFacilities(): Response<List<FacilityEntity>>
}