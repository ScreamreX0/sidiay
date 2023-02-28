package com.example.sidiay.di

import android.content.Context
import com.example.data.network.api.ApiService
import com.example.data.repositories.TicketsRepository
import com.example.data.repositories.UserRepository
import com.example.domain.repositories.ITicketsRepository
import com.example.domain.repositories.IUserRepository
import com.example.data.datastores.ConnectionsDataStore
import com.example.data.repositories.AuthRepository
import com.example.domain.repositories.IAuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    fun provideUserRepository(apiService: ApiService): IUserRepository = UserRepository(apiService)
    @Provides
    fun provideTicketRepository(apiService: ApiService): ITicketsRepository = TicketsRepository(apiService)
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): ConnectionsDataStore = ConnectionsDataStore(context)
    @Provides
    fun provideAuthRepository(apiService: ApiService): IAuthRepository = AuthRepository(apiService)
}