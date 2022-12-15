package com.example.domain.usecases.menu.create

import com.example.domain.models.params.EmployeeParams
import com.example.domain.repositories.IEmployeesRepository
import com.example.domain.utils.Constants
import javax.inject.Inject

class GetEmployeesUseCase @Inject constructor(
    private val employeesRepository: IEmployeesRepository
) {
    suspend fun execute(): List<EmployeeParams> {
        if (Constants.DEBUG_MODE) {
            return employeesRepository.getTestEmployees()
        }
        return employeesRepository.getEmployees()
    }
}