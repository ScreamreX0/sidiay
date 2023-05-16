package com.example.data.repositories

import com.example.core.utils.Endpoints
import com.example.data.network.api.ApiService
import com.example.domain.data_classes.entities.*
import com.example.domain.data_classes.params.TicketData
import com.example.domain.repositories.ITicketDataRepository
import javax.inject.Inject

class TicketDataRepository @Inject constructor(
    private val apiService: ApiService
) : ITicketDataRepository {
    override suspend fun getTicketData(url: String): Pair<Int, TicketData?> {
        val result = apiService.getTicketData("$url${Endpoints.Tickets.GET_DATA}")
        return Pair(result.code(), result.body())
    }

    override suspend fun getTicketData(): TicketData {
        return TicketData(
            users = getUsers(),
            facilities = getFacilities(),
            transport = getTransport()
        )
    }

    private fun getUsers() = listOf(
        UserEntity(id = 0, EmployeeEntity(id = 0, name = "Имя№1")),
        UserEntity(id = 1, EmployeeEntity(id = 1, name = "Имя№2")),
        UserEntity(id = 2, EmployeeEntity(id = 2, name = "Имя№3")),
        UserEntity(id = 3, EmployeeEntity(id = 3, name = "Имя№4")),
        UserEntity(id = 4, EmployeeEntity(id = 4, name = "Имя№5")),
        UserEntity(id = 5, EmployeeEntity(id = 5, name = "Имя№6")),
        UserEntity(id = 6, EmployeeEntity(id = 6, name = "Имя№7")),
        UserEntity(id = 7, EmployeeEntity(id = 7, name = "Имя№8")),
        UserEntity(id = 8, EmployeeEntity(id = 8, name = "Имя№9"))
    )

    private fun getFacilities() = listOf(
        FacilityEntity(id = 0, name = "Объект номер 1"),
        FacilityEntity(id = 1, name = "Объект номер 2"),
        FacilityEntity(id = 2, name = "Объект номер 3"),
        FacilityEntity(id = 3, name = "Объект номер 4"),
        FacilityEntity(id = 4, name = "Объект номер 5"),
        FacilityEntity(id = 5, name = "Объект номер 6"),
        FacilityEntity(id = 6, name = "Объект номер 7"),
        FacilityEntity(id = 7, name = "Объект номер 8"),
        FacilityEntity(id = 8, name = "Объект номер 9"),
    )

    private fun getTransport() = listOf(
        TransportEntity(id = 0, "Transport#0"),
        TransportEntity(id = 8, "Transport#8"),
        TransportEntity(id = 9, "Transport#9"),
        TransportEntity(id = 1, "Transport#1"),
        TransportEntity(id = 2, "Transport#2"),
        TransportEntity(id = 3, "Transport#3"),
        TransportEntity(id = 4, "Transport#4"),
        TransportEntity(id = 5, "Transport#5"),
        TransportEntity(id = 6, "Transport#6"),
        TransportEntity(id = 7, "Transport#7"),
    )
}