package com.example.domain.usecases.settings

import com.example.domain.repositories.IThemeDataStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(
    private val settingsDataStore: IThemeDataStore
) {
    suspend fun execute() = settingsDataStore.getMode.first()?.let { it.toBoolean() } ?: true
}