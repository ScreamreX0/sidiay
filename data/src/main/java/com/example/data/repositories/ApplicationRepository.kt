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
                service = "${it}Ихсанов",
                description = "Покрасить подставку под КТП, ТД, ТП, ТО",
                priority = "Средний",
                status = "Не закрыта",
                executor = Employee(
                    id = it * 2,
                    firstName = "$it",
                    name = "${it + 1}",
                    lastName = "${it + 2}"
                ),
                plannedDate = "05-03-2003",
                expirationDate = "05-03-2003",
                author = Employee(
                    id = it * 3,
                    firstName = "$it",
                    name = "${it + 1}",
                    lastName = "${it + 2}"
                ),
                creationDate = "05-03-2003",
                objects = listOf(Object(id = 1), Object(id = 2), Object(id = 3)),
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