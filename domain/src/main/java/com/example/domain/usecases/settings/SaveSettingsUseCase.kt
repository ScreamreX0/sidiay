package com.example.domain.usecases.settings

import com.example.domain.repositories.ISettingsDataStore
import javax.inject.Inject

class SaveSettingsUseCase @Inject constructor(
    private val settingsDataStore: ISettingsDataStore
) {
    suspend fun execute(darkMode: Boolean) = settingsDataStore.saveMode(darkMode)
}