package com.example.sidiay.di

import android.content.Context
import com.example.data.datastore.ConnectionsDataStore
import com.example.data.datastore.TicketsDataStore
import com.example.data.datastore.SettingsDataStore
import com.example.data.network.api.ApiService
import com.example.data.repositories.AuthRepository
import com.example.data.repositories.NetworkConnectionRepository
import com.example.data.repositories.TicketDataRepository
import com.example.data.repositories.TicketsHistoryRepository
import com.example.data.repositories.TicketsRepository
import com.example.domain.repositories.IAuthRepository
import com.example.domain.repositories.IConnectionsDataStore
import com.example.domain.repositories.ITicketsDataStore
import com.example.domain.repositories.INetworkConnectionRepository
import com.example.domain.repositories.ISettingsDataStore
import com.example.domain.repositories.ITicketDataRepository
import com.example.domain.repositories.ITicketsHistoryRepository
import com.example.domain.repositories.ITicketsRepository
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

    @Provides
    fun provideNetworkConnectionRepository(apiService: ApiService): INetworkConnectionRepository = NetworkConnectionRepository(apiService)

    @Provides
    fun provideTicketsHistoryRepository(apiService: ApiService): ITicketsHistoryRepository = TicketsHistoryRepository(apiService)

    // Datastore
    @Provides
    fun provideConnectionsDataStore(@ApplicationContext context: Context): IConnectionsDataStore = ConnectionsDataStore(context)

    @Provides
    fun provideSettingsDataStore(@ApplicationContext context: Context): ISettingsDataStore = SettingsDataStore(context)

    @Provides
    fun provideDraftsDataStore(@ApplicationContext context: Context): ITicketsDataStore = TicketsDataStore(context)
}