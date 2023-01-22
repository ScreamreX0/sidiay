package com.example.data.repositories

import com.example.data.api.ApiService
import com.example.domain.models.entities.Ticket
import com.example.domain.models.entities.Object
import com.example.domain.models.entities.User
import com.example.domain.repositories.ITicketsRepository
import com.example.domain.utils.Debugger
import javax.inject.Inject

class TicketsRepository @Inject constructor(
    private val apiService: ApiService
) : ITicketsRepository {
    override suspend fun get(): Pair<Int, List<Ticket>?> {
        val result = apiService.getTickets()
        Debugger.printInfo("Getting tickets from api. Result code:${result.code()}")
        return Pair(result.code(), result.body())
    }

    override suspend fun get(id: Int): Ticket {
        TODO("Not yet implemented")
    }

    override suspend fun set(newTicket: Ticket): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun add(ticket: Ticket): Boolean {
        TODO("Not yet implemented")
    }

    // Test
    override suspend fun getTest(): List<Ticket> {
        return List(10) {
            getTest(it)
        }
    }

    override suspend fun getTest(id: Int): Ticket {
        return Ticket(
            id = id,
            title = "Неисправность №$id",
            service = "Сервис №$id",
            executor = User(
                id = id,
                firstName = "Ихсанов№${id * 2}",
                name = "Руслан№${id * 2}",
                lastName = "Ленарович№${id * 2}"
            ),
            type = "Ведущий",
            priority = "Средний",
            status = "Не закрыта",
            plannedDate = "11.12.2022 15:00",
            expirationDate = "11.12.2022 15:00",
            description = "Покрасить подставку под КТП, ТД, ТП, ТО",
            completedWorks = "Неисправность ЧРЭП. Выполнена замена СУ с ЧП. Электромонтер Карзохин Н.И. 22:11-23:30.",
            author = User(
                id = id,
                firstName = "Ихсанов№${id * 3}",
                name = "Руслан№${id * 3}",
                lastName = "Ленарович№${id * 3}"
            ),
            creationDate = "05.12.2022 00:00",
            objects = listOf(
                Object(
                    id = 1,
                    name = "ЦДНГ-1"
                ), Object(
                    id = 2,
                    name = "Сунчелеевское"
                ), Object(
                    id = 3,
                    name = "ФФФФ-123"
                )
            ),
        )
    }

    override suspend fun setTest(newTicket: Ticket): Boolean {
        return true
    }

    override suspend fun addTest(ticket: Ticket): Boolean {
        return true
    }
}