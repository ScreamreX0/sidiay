package com.example.domain.usecases.settings

import com.example.domain.repositories.ISettingsDataStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(
    private val settingsDataStore: ISettingsDataStore
) {
    suspend fun execute() = settingsDataStore.getMode.first()?.let { it.toBoolean() } ?: true
}