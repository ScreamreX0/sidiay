package com.example.data.repositories

import com.example.core.utils.Endpoints
import com.example.data.network.api.ApiService
import com.example.domain.data_classes.entities.*
import com.example.domain.data_classes.params.TicketData
import com.example.domain.repositories.ITicketDataRepository
import java.lang.Math.random
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

    private fun getUsers() = List(10) {
        UserEntity(id = it.toLong(), EmployeeEntity(id =  it.toLong(), name = "Имя№$it", jobTitle = it))
        UserEntity(id = it.toLong(), EmployeeEntity(id =  it.toLong(), name = "Имя№$it", jobTitle = it))
        UserEntity(id = it.toLong(), EmployeeEntity(id =  it.toLong(), name = "Имя№$it", jobTitle = it))
        UserEntity(id = it.toLong(), EmployeeEntity(id =  it.toLong(), name = "Имя№$it", jobTitle = it))
        UserEntity(id = it.toLong(), EmployeeEntity(id =  it.toLong(), name = "Имя№$it", jobTitle = it))
        UserEntity(id = it.toLong(), EmployeeEntity(id =  it.toLong(), name = "Имя№$it", jobTitle = it))
        UserEntity(id = it.toLong(), EmployeeEntity(id =  it.toLong(), name = "Имя№$it", jobTitle = it))
        UserEntity(id = it.toLong(), EmployeeEntity(id =  it.toLong(), name = "Имя№$it", jobTitle = it))
        UserEntity(id = it.toLong(), EmployeeEntity(id =  it.toLong(), name = "Имя№$it", jobTitle = it))
    }

    private fun getFacilities() = List(10) {
        FacilityEntity(id = it.toLong(), name = "Объект номер ${it}")
        FacilityEntity(id = it.toLong(), name = "Объект номер ${it}")
        FacilityEntity(id = it.toLong(), name = "Объект номер ${it}")
        FacilityEntity(id = it.toLong(), name = "Объект номер ${it}")
        FacilityEntity(id = it.toLong(), name = "Объект номер ${it}")
        FacilityEntity(id = it.toLong(), name = "Объект номер ${it}")
        FacilityEntity(id = it.toLong(), name = "Объект номер ${it}")
        FacilityEntity(id = it.toLong(), name = "Объект номер ${it}")
        FacilityEntity(id = it.toLong(), name = "Объект номер ${it}")
    }

    private fun getTransport() = List(10) {
        TransportEntity(id = it.toLong(), "Transport#$it")
        TransportEntity(id = it.toLong(), "Transport#$it")
        TransportEntity(id = it.toLong(), "Transport#$it")
        TransportEntity(id = it.toLong(), "Transport#$it")
        TransportEntity(id = it.toLong(), "Transport#$it")
        TransportEntity(id = it.toLong(), "Transport#$it")
        TransportEntity(id = it.toLong(), "Transport#$it")
        TransportEntity(id = it.toLong(), "Transport#$it")
        TransportEntity(id = it.toLong(), "Transport#$it")
        TransportEntity(id = it.toLong(), "Transport#$it")
    }
}