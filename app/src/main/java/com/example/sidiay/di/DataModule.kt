package com.example.sidiay.di

import com.example.data.api.ApiService
import com.example.data.repositories.ApplicationRepository
import com.example.data.repositories.EmployeesRepository
import com.example.data.repositories.UserRepository
import com.example.domain.repositories.IApplicationRepository
import com.example.domain.repositories.IEmployeesRepository
import com.example.domain.repositories.IUserRepository
import com.example.domain.utils.Constants
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

    @Provides
    fun provideApplicationRepository(apiService: ApiService): IApplicationRepository {
        return ApplicationRepository(apiService)
    }

    @Provides
    fun provideEmployeesRepository(apiService: ApiService): IEmployeesRepository {
        return EmployeesRepository(apiService)
    }
}