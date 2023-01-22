package com.example.data.repositories

import com.example.data.api.ApiService
import com.example.domain.models.entities.Ticket
import com.example.domain.models.entities.Facility
import com.example.domain.models.entities.Kind
import com.example.domain.models.entities.User
import com.example.domain.repositories.ITicketsRepository
import com.example.domain.utils.Debugger
import retrofit2.Call
import retrofit2.Response
import java.sql.Date
import javax.inject.Inject
import javax.security.auth.callback.Callback

class TicketsRepository @Inject constructor(
    private val apiService: ApiService
) : ITicketsRepository {
    override suspend fun get(): Pair<Int, List<Ticket>?> {
        Debugger.printInfo("Getting tickets from api")
        val result = apiService.getTickets()
        Debugger.printInfo("Getting tickets success. Result code ${result.code()}")
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
            id = id.toLong(),
            name = "Неисправность №$id",
            service = "Сервис №$id",
            executor = User(
                id = id.toLong(),
                firstName = "Ихсанов№${id * 2}",
                name = "Руслан№${id * 2}",
                lastName = "Ленарович№${id * 2}"
            ),
            priority = 3,
            status = "Не закрыта",
            planeDate = "22.01.23",
            expirationDate = "22.01.23",
            description = "Покрасить подставку под КТП, ТД, ТП, ТО",
            completedWork = "Неисправность ЧРЭП. Выполнена замена СУ с ЧП. Электромонтер Карзохин Н.И. 22:11-23:30.",
            author = User(
                id = id.toLong(),
                firstName = "Ихсанов№${id * 3}",
                name = "Руслан№${id * 3}",
                lastName = "Ленарович№${id * 3}"
            ),
            creationDate = "22.01.23",
            facilities = listOf(
                Facility(
                    id = 1,
                    name = "ЦДНГ-1"
                ), Facility(
                    id = 2,
                    name = "Сунчелеевское"
                ), Facility(
                    id = 3,
                    name = "ФФФФ-123"
                )
            ),
            kind = Kind(
                id = 1,
                name = "ТКО"
            )
        )
    }

    override suspend fun setTest(newTicket: Ticket): Boolean {
        return true
    }

    override suspend fun addTest(ticket: Ticket): Boolean {
        return true
    }
}