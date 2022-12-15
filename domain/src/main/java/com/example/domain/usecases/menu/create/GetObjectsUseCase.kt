package com.example.domain.usecases.menu.create

import com.example.domain.repositories.IObjectsRepository
import com.example.domain.utils.Constants
import javax.inject.Inject

class GetObjectsUseCase @Inject constructor(
    private val objectsRepository: IObjectsRepository
) {
    suspend fun execute(): List<String> {
        if (Constants.DEBUG_MODE) {
            return objectsRepository.getTestObjects()
        }
        return objectsRepository.getObjects()
    }
}