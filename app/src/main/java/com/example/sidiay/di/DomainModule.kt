package com.example.sidiay.di

import android.content.Context
import com.example.domain.repositories.IAuthRepository
import com.example.domain.repositories.IConnectionsDataStore
import com.example.domain.repositories.ITicketsDataStore
import com.example.domain.repositories.INetworkConnectionRepository
import com.example.domain.repositories.IThemeDataStore
import com.example.domain.repositories.ITicketsRepository
import com.example.domain.usecases.connections.CheckConnectionUseCase
import com.example.domain.usecases.connections.GetConnectionsUseCase
import com.example.domain.usecases.connections.SaveConnectionsUseCase
import com.example.domain.usecases.drafts.DeleteDraftsUseCase
import com.example.domain.usecases.settings.GetSettingsUseCase
import com.example.domain.usecases.settings.SaveSettingsUseCase
import com.example.domain.usecases.signin.CheckSignInFieldsUseCase
import com.example.domain.usecases.signin.SignInUseCase
import com.example.domain.usecases.drafts.GetDraftsUseCase
import com.example.domain.usecases.tickets.GetTicketsUseCase
import com.example.domain.usecases.drafts.SaveDraftsUseCase
import com.example.domain.usecases.tickets.SaveTicketUseCase
import com.example.domain.usecases.tickets.UpdateTicketUseCase
import com.example.domain.usecases.ticket_restrictions.GetTicketCreateRestrictionsUseCase
import com.example.domain.usecases.ticket_restrictions.GetTicketUpdateRestrictionsUseCase
import com.example.domain.usecases.tickets.DeleteTicketsUseCase
import com.example.domain.usecases.tickets.FilterTicketsListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {
    @Provides
    fun provideCheckSignInFieldsUseCase(): CheckSignInFieldsUseCase = CheckSignInFieldsUseCase()

    @Provides
    fun provideSignInUseCase(
        userRepository: IAuthRepository,
        checkConnectionUseCase: CheckConnectionUseCase,
        checkSignInFieldsUseCase: CheckSignInFieldsUseCase
    ): SignInUseCase = SignInUseCase(userRepository, checkConnectionUseCase, checkSignInFieldsUseCase)

    @Provides
    fun provideGetTicketsUseCase(
        ticketsRepository: ITicketsRepository,
        checkConnectionUseCase: CheckConnectionUseCase,
        ticketDataStore: ITicketsDataStore
    ): GetTicketsUseCase = GetTicketsUseCase(ticketsRepository, checkConnectionUseCase, ticketDataStore)

    @Provides
    fun provideDeleteTicketsUseCase(ticketDataStore: ITicketsDataStore): DeleteTicketsUseCase =
        DeleteTicketsUseCase(ticketDataStore)

    @Provides
    fun provideSaveTicketUseCase(
        ticketsRepository: ITicketsRepository,
        checkConnectionUseCase: CheckConnectionUseCase,
        ticketDataStore: ITicketsDataStore
    ): SaveTicketUseCase = SaveTicketUseCase(ticketsRepository, checkConnectionUseCase, ticketDataStore)

    @Provides
    fun provideUpdateTicketUseCase(
        ticketsRepository: ITicketsRepository,
        checkConnectionUseCase: CheckConnectionUseCase,
        ticketDataStore: ITicketsDataStore
    ): UpdateTicketUseCase = UpdateTicketUseCase(ticketsRepository, checkConnectionUseCase, ticketDataStore)

    @Provides
    fun provideGetTicketUpdateRestrictionsUseCase(): GetTicketUpdateRestrictionsUseCase = GetTicketUpdateRestrictionsUseCase()

    @Provides
    fun provideGetTicketCreateRestrictionsUseCase(): GetTicketCreateRestrictionsUseCase = GetTicketCreateRestrictionsUseCase()

    @Provides
    fun provideCheckConnectionUseCase(
        @ApplicationContext context: Context,
        networkConnectionRepository: INetworkConnectionRepository
    ): CheckConnectionUseCase = CheckConnectionUseCase(networkConnectionRepository, context)

    @Provides
    fun provideGetConnectionsUseCase(connectionsDataStore: IConnectionsDataStore): GetConnectionsUseCase =
        GetConnectionsUseCase(connectionsDataStore)

    @Provides
    fun provideGetDraftsUseCase(draftsDataStore: ITicketsDataStore): GetDraftsUseCase = GetDraftsUseCase(draftsDataStore)

    @Provides
    fun provideDeleteDraftsUseCase(draftsDataStore: ITicketsDataStore): DeleteDraftsUseCase = DeleteDraftsUseCase(draftsDataStore)

    @Provides
    fun provideGetSettingsUseCase(themeDataStore: IThemeDataStore): GetSettingsUseCase = GetSettingsUseCase(themeDataStore)

    @Provides
    fun provideSaveConnectionsUseCase(connectionsDataStore: IConnectionsDataStore): SaveConnectionsUseCase =
        SaveConnectionsUseCase(connectionsDataStore)

    @Provides
    fun provideSaveDraftsUseCase(draftsDataStore: ITicketsDataStore, getDraftsUseCase: GetDraftsUseCase): SaveDraftsUseCase =
        SaveDraftsUseCase(draftsDataStore, getDraftsUseCase)

    @Provides
    fun provideSaveSettingsUseCase(themeDataStore: IThemeDataStore): SaveSettingsUseCase = SaveSettingsUseCase(themeDataStore)

    @Provides
    fun provideFilterTicketsListUseCase(): FilterTicketsListUseCase = FilterTicketsListUseCase()
}