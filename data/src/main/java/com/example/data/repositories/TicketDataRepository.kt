package com.example.data.repositories

import com.example.data.network.api.ApiService
import com.example.domain.data_classes.entities.EmployeeEntity
import com.example.domain.data_classes.entities.FacilityEntity
import com.example.domain.data_classes.entities.TransportEntity
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.TicketData
import com.example.domain.repositories.ITicketDataRepository
import javax.inject.Inject

class TicketDataRepository @Inject constructor(
    private val apiService: ApiService
) : ITicketDataRepository {
    override suspend fun getTicketData(url: String): Pair<Int, TicketData?> {
        val result = apiService.getTicketData(url)
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