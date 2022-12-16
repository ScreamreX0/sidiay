package com.example.domain.usecases.menu.create

import com.example.domain.repositories.IObjectsRepository
import com.example.domain.utils.Constants
import com.example.domain.models.entities.Object
import javax.inject.Inject

class GetObjectsUseCase @Inject constructor(
    private val objectsRepository: IObjectsRepository
) {
    suspend fun execute(): List<Object> {
        if (Constants.DEBUG_MODE) {
            return objectsRepository.getTestObjects()
        }
        return objectsRepository.getObjects()
    }
}