package com.example.data.repositories

import com.example.data.api.ApiService
import com.example.domain.models.params.EmployeeParams
import com.example.domain.repositories.IEmployeesRepository
import javax.inject.Inject

class EmployeesRepository @Inject constructor(
    private val apiService: ApiService
) : IEmployeesRepository {
    override suspend fun getTestEmployees(): List<EmployeeParams> {
        return List(15) {
            EmployeeParams(
                id = it,
                firstName = "Ikhsanov$it",
                name = "Ruslan$it",
                lastName = "Lenarovich$it",
                isSelected = false
            )
        }
    }

    override suspend fun getEmployees(): List<EmployeeParams> {
        TODO("Not yet implemented")
    }
}