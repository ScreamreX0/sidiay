package com.example.data.api

import com.example.domain.models.entities.Employee
import com.example.domain.models.entities.User
import com.example.domain.models.entities.Object
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/users/email-in")
    suspend fun emailIn(@Body body: HashMap<String, Any>): Response<User>

    @POST("/objects")
    suspend fun getObjects(@Body body: HashMap<String, Any>): Response<List<Object>>

    @POST("/employees")
    suspend fun getEmployees(@Body body: HashMap<String, Any>): Response<List<Employee>>
}