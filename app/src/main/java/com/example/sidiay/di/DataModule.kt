package com.example.sidiay.di

import android.content.Context
import com.example.data.network.api.ApiService
import com.example.data.repositories.TicketsRepository
import com.example.domain.repositories.ITicketsRepository
import com.example.data.datastore.ConnectionsDataStore
import com.example.data.datastore.ThemeDataStore
import com.example.data.repositories.AuthRepository
import com.example.data.repositories.TicketDataRepository
import com.example.domain.repositories.IAuthRepository
import com.example.domain.repositories.ITicketDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    // Repositories
    @Provides
    fun provideAuthRepository(apiService: ApiService): IAuthRepository = AuthRepository(apiService)
    @Provides
    fun provideTicketRepository(apiService: ApiService): ITicketsRepository = TicketsRepository(apiService)
    @Provides
    fun provideTicketDataRepository(apiService: ApiService): ITicketDataRepository = TicketDataRepository(apiService)

    // Datastores
    @Provides
    fun provideConnectionsDataStore(@ApplicationContext context: Context): ConnectionsDataStore = ConnectionsDataStore(context)
    @Provides
    fun provideThemeDataStore(@ApplicationContext context: Context): ThemeDataStore = ThemeDataStore(context)
}