package com.example.sidiay.di

import android.content.Context
import com.example.data.api.ApiService
import com.example.data.repositories.TicketsRepository
import com.example.data.repositories.UserRepository
import com.example.domain.repositories.ITicketsRepository
import com.example.domain.repositories.IUserRepository
import com.example.signin.data.ConnectionsDataStore
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
    fun provideTicketRepository(apiService: ApiService): ITicketsRepository =
        TicketsRepository(apiService)

    @Provides
    fun provideDataStore(@ApplicationContext context: Context): ConnectionsDataStore =
        ConnectionsDataStore(context)
}