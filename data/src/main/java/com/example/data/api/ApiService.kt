package com.example.data.api

import com.example.domain.models.entities.User
import com.example.domain.models.params.EmployeeParams
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/users/email-in")
    suspend fun emailIn(@Body body: HashMap<String, Any>): Response<User>

    @POST("/employees")
    suspend fun getEmployees(@Body body: HashMap<String, Any>): Response<List<EmployeeParams>>
}