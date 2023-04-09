package com.example.data.repositories

import com.example.data.network.api.ApiService
import com.example.domain.data_classes.entities.*
import com.example.domain.data_classes.params.TicketData
import com.example.domain.repositories.ITicketDataRepository
import javax.inject.Inject

class TicketDataRepository @Inject constructor(
    private val apiService: ApiService
) : ITicketDataRepository {

    override suspend fun getTicketData(url: String): Pair<Int, TicketData?> {
        val endpoint = "/tickets/get-data"
        val result = apiService.getTicketData(url + endpoint)
        return Pair(result.code(), result.body())
    }

    override suspend fun getTicketData(): TicketData {
        return TicketData(
            kinds = getKinds(),
            priorities = getPriorities(),
            users = getUsers(),
            equipment = getEquipment(),
            facilities = getFacilities(),
            services = getServices(),
            transport = getTransport()
        )
    }

    private fun getKinds() = listOf(
        KindEntity(id = 0, name = "Плановые"),
        KindEntity(id = 1, name = "Текущий"),
        KindEntity(id = 2, name = "Капитальный"),
        KindEntity(id = 3, name = "ТО"),
        KindEntity(id = 4, name = "ППР"),
        KindEntity(id = 5, name = "Пробы обводнен"),
        KindEntity(id = 6, name = "Стропальные работы"),
    )

    private fun getPriorities() = listOf(
        PriorityEntity(id = 0, name = "Очень низкий", value = 1),
        PriorityEntity(id = 1, name = "Низкий", value = 2),
        PriorityEntity(id = 2, name = "Средний", value = 3),
        PriorityEntity(id = 3, name = "Высокий", value = 4),
        PriorityEntity(id = 4, name = "Срочный", value = 5),
    )

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

    private fun getEquipment() = listOf(
        EquipmentEntity(id = 0, "Equipment#1"),
        EquipmentEntity(id = 1, "Equipment#1"),
        EquipmentEntity(id = 2, "Equipment#2"),
        EquipmentEntity(id = 3, "Equipment#3"),
        EquipmentEntity(id = 4, "Equipment#4"),
        EquipmentEntity(id = 5, "Equipment#5"),
        EquipmentEntity(id = 6, "Equipment#6"),
        EquipmentEntity(id = 7, "Equipment#7"),
        EquipmentEntity(id = 8, "Equipment#8"),
        EquipmentEntity(id = 9, "Equipment#9")
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

    private fun getServices() = listOf(
        ServiceEntity(id = 0, name = "НПО"),
        ServiceEntity(id = 1, name = "Энерго"),
        ServiceEntity(id = 2, name = "КИП"),
        ServiceEntity(id = 3, name = "Сварочные работы"),
        ServiceEntity(id = 4, name = "ПРС"),
        ServiceEntity(id = 5, name = "Исследование"),
        ServiceEntity(id = 6, name = "Строительные работы"),
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