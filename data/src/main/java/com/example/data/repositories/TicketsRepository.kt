package com.example.data.repositories

import com.example.data.api.ApiService
import com.example.domain.models.entities.TicketEntity
import com.example.domain.models.entities.FacilityEntity
import com.example.domain.models.entities.KindEntity
import com.example.domain.models.entities.UserEntity
import com.example.domain.repositories.ITicketsRepository
import com.example.domain.utils.Debugger
import javax.inject.Inject
import kotlin.random.Random

class TicketsRepository @Inject constructor(
    private val apiService: ApiService
) : ITicketsRepository {
    override suspend fun get(): Pair<Int, List<TicketEntity>?> {
        Debugger.printInfo("Getting tickets from api")
        val result = apiService.getTickets()
        Debugger.printInfo("Getting tickets success. Result code ${result.code()}")
        return Pair(result.code(), result.body())
    }

    override suspend fun get(id: Int): TicketEntity {
        TODO("Not yet implemented")
    }

    override suspend fun set(newTicketEntity: TicketEntity): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun add(ticketEntity: TicketEntity): Boolean {
        TODO("Not yet implemented")
    }

    // Test
    override suspend fun getTest(): List<TicketEntity> {
        return List(10) {
            getTest(it)
        }
    }

    override suspend fun getTest(id: Int): TicketEntity {
        return TicketEntity(
            id = id.toLong(),
            name = "Неисправность №$id",
            service = "Сервис №$id",
            executor = UserEntity(
                id = id.toLong(),
                firstname = "Ихсанов№${id * 2}",
                name = "Руслан№${id * 2}",
                lastname = "Ленарович№${id * 2}"
            ),
            priority = Random.nextInt(1, 6),
            status = "Не закрыта",
            plane_date = "22.01.23",
            expiration_date = "22.01.23",
            description = "Покрасить подставку под КТП, ТД, ТП, ТО",
            completed_work = "Неисправность ЧРЭП. Выполнена замена СУ с ЧП. Электромонтер Карзохин Н.И. 22:11-23:30.",
            author = UserEntity(
                id = id.toLong(),
                firstname = "Ихсанов№${id * 3}",
                name = "Руслан№${id * 3}",
                lastname = "Ленарович№${id * 3}"
            ),
            creation_date = "22.01.23",
            facilities = listOf(
                FacilityEntity(
                    id = 1,
                    name = "ЦДНГ-1"
                ), FacilityEntity(
                    id = 2,
                    name = "Сунчелеевское"
                ), FacilityEntity(
                    id = 3,
                    name = "ФФФФ-123"
                )
            ),
            kindEntity = KindEntity(
                id = 1,
                name = "ТКО"
            )
        )
    }

    override suspend fun setTest(newTicketEntity: TicketEntity): Boolean {
        return true
    }

    override suspend fun addTest(ticketEntity: TicketEntity): Boolean {
        return true
    }
}