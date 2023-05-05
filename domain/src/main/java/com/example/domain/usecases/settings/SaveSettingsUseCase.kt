package com.example.domain.usecases.settings

import com.example.domain.repositories.IThemeDataStore
import javax.inject.Inject

class SaveSettingsUseCase @Inject constructor(
    private val settingsDataStore: IThemeDataStore
) {
    suspend fun execute(darkMode: Boolean) = settingsDataStore.saveMode(darkMode)
}