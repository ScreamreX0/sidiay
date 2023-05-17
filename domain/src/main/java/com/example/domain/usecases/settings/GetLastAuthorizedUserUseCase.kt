package com.example.domain.usecases.settings

import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.repositories.ISettingsDataStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetLastAuthorizedUserUseCase @Inject constructor(
    private val settingsDataStore: ISettingsDataStore
) {
    suspend fun execute(): UserEntity? = settingsDataStore.getLastAuthorizedUser.first()
}