package com.example.domain.repositories

import com.example.domain.data_classes.entities.UserEntity
import kotlinx.coroutines.flow.Flow

interface ISettingsDataStore {
    val getMode: Flow<String?>
    suspend fun saveMode(darkMode: Boolean)

    val getLastAuthorizedUser: Flow<UserEntity?>
    suspend fun saveLastAuthorizedUser(user: UserEntity)
}