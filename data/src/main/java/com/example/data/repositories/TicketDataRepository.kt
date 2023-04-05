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
        val result = apiService.getTicketData(url)
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

    private fun getUsers() = listOf<UserEntity>(TODO("add some users"))

    private fun getEquipment() = listOf<EquipmentEntity>(TODO("add some equipment"))

    private fun getFacilities() = listOf<FacilityEntity>(TODO("add some facilities"))

    private fun getServices() = listOf(
        ServiceEntity(id = 0, name = "НПО"),
        ServiceEntity(id = 1, name = "Энерго"),
        ServiceEntity(id = 2, name = "КИП"),
        ServiceEntity(id = 3, name = "Сварочные работы"),
        ServiceEntity(id = 4, name = "ПРС"),
        ServiceEntity(id = 5, name = "Исследование"),
        ServiceEntity(id = 6, name = "Строительные работы"),
    )

    private fun getTransport() = listOf<TransportEntity>(TODO("add some transport"))

}