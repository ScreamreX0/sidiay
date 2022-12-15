package com.example.domain.repositories

import com.example.domain.models.params.EmployeeParams

interface IEmployeesRepository {
    suspend fun getTestEmployees(): List<EmployeeParams>
    suspend fun getEmployees(): List<EmployeeParams>
}