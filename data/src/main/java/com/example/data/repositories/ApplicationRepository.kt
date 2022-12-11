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
    override fun getApplicationsListOffline(): List<Application> {
        return List(10) {
            Application(
                id = it,
                title = "Неисправность №$it",
                service = "Сервис №$it",
                executor = Employee(
                    id = it,
                    firstName = "Ихсанов№${it * 2}",
                    name = "Руслан№${it * 2}",
                    lastName = "Ленарович№${it * 2}"
                ),
                type = "Ведущий",
                priority = "Средний",
                status = "Не закрыта",
                plannedDate = "11.12.2022 15:00",
                expirationDate = "11.12.2022 15:00",
                description = "Покрасить подставку под КТП, ТД, ТП, ТО",
                completedWorks = "Неисправность ЧРЭП. Выполнена замена СУ с ЧП. Электромонтер Карзохин Н.И. 22:11-23:30.",
                author = Employee(
                    id = it,
                    firstName = "Ихсанов№${it * 3}",
                    name = "Руслан№${it * 3}",
                    lastName = "Ленарович№${it * 3}"
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
    }

    override suspend fun getApplicationsList(): List<Application> {
        TODO("Not yet implemented")
    }

    override fun getApplicationOffline(id: Int): Application {
        TODO("Not yet implemented")
    }

    override suspend fun getApplication(id: Int): Application {
        TODO("Not yet implemented")
    }

    override fun changeApplicationOffline(newApplication: Application): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun changeApplication(newApplication: Application): Boolean {
        TODO("Not yet implemented")
    }
}