package com.example.domain.usecases.settings

import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.repositories.ISettingsDataStore
import javax.inject.Inject

class SaveLastAuthorizedUseCase @Inject constructor(
    private val settingsDataStore: ISettingsDataStore
) {
    suspend fun execute(user: UserEntity?) = user?.let { settingsDataStore.saveLastAuthorizedUser(it) }
}