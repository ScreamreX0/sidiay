package com.example.data.network.api

import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.Credentials
import com.example.domain.data_classes.params.TicketData
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    /** Connections */
    @GET
    suspend fun checkConnection(@Url url: String): Response<Void>

    /** Users */
    @POST
    @Multipart
    suspend fun signIn(@Url url: String, @Body credentials: Credentials): Response<UserEntity>

    @GET
    suspend fun getUsers(@Url url: String): Response<List<UserEntity>>

    /** Tickets */
    @GET
    suspend fun getTickets(@Url url: String): Response<List<TicketEntity>>

    @POST
    suspend fun addTicket(@Url url: String, @Body ticket: TicketEntity): Response<TicketEntity>

    @POST
    suspend fun updateTicket(@Url url: String, @Body ticket: TicketEntity): Response<TicketEntity>

    @GET
    suspend fun getTicketData(@Url url: String): Response<TicketData>
}