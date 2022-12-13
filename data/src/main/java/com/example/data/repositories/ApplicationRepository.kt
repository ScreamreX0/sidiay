package com.example.data.repositories

import com.example.data.api.ApiService
import com.example.domain.models.entities.Application
import com.example.domain.models.entities.Employee
import com.example.domain.models.entities.Object
import com.example.domain.repositories.IApplicationRepository
import javax.inject.Inject

class ApplicationRepository @Inject constructor(
    apiService: ApiService
) : IApplicationRepository {
    override suspend fun getApplicationsList(): List<Application> {
        TODO("Not yet implemented")
    }

    override suspend fun getApplication(id: Int): Application {
        TODO("Not yet implemented")
    }

    override suspend fun changeApplication(newApplication: Application): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun saveApplication(application: Application): Boolean {
        TODO("Not yet implemented")
    }

    // Test
    override suspend fun getTestApplicationsList(): List<Application> {
        return List(10) {
            getTestApplication(it)
        }
    }

    override suspend fun getTestApplication(id: Int): Application {
        return Application(
            id = id,
            title = "Неисправность №$id",
            service = "Сервис №$id",
            executor = Employee(
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
            author = Employee(
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

    override suspend fun testChangeApplication(newApplication: Application): Boolean {
        return true
    }

    override suspend fun saveTestApplication(application: Application): Boolean {
        return true
    }
}