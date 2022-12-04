package com.example.sidiay.di

import com.example.data.api.ApiService
import com.example.data.repositories.UserRepository
import com.example.domain.repositories.IUserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    fun provideUserRepository(apiService: ApiService): IUserRepository {
        return UserRepository(apiService)
    }
}