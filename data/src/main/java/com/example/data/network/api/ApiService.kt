package com.example.data.network.api

import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.Credentials
import com.example.domain.data_classes.params.TicketData
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    //
    // CONNECTIONS
    //
    @GET
    suspend fun checkConnection(@Url url: String): Response<Void>

    //
    // AUTHENTICATION
    //
    @POST
    suspend fun signIn(@Url url: String, @Body credentials: Credentials): Response<UserEntity>

    //
    // TICKETS
    //
    // Tickets get
    @GET
    suspend fun getTickets(@Url url: String): Response<List<TicketEntity>>

    // Ticket add
    @POST
    suspend fun addTicket(@Url url: String, @Body ticket: TicketEntity): Response<TicketEntity>

    // Ticket update
    @POST
    suspend fun updateTicket(@Url url: String, @Body ticket: TicketEntity): Response<TicketEntity>

    // Ticket data
    @GET
    suspend fun getTicketData(@Url url: String): Response<TicketData>
}