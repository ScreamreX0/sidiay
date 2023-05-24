package com.example.sidiay.di

import android.content.Context
import com.example.domain.repositories.IAuthRepository
import com.example.domain.repositories.IConnectionsDataStore
import com.example.domain.repositories.INetworkConnectionRepository
import com.example.domain.repositories.ISettingsDataStore
import com.example.domain.repositories.ITicketsDataStore
import com.example.domain.repositories.ITicketsHistoryRepository
import com.example.domain.repositories.ITicketsRepository
import com.example.domain.usecases.connections.CheckConnectionUseCase
import com.example.domain.usecases.connections.GetConnectionsUseCase
import com.example.domain.usecases.connections.SaveConnectionsUseCase
import com.example.domain.usecases.drafts.DeleteDraftsUseCase
import com.example.domain.usecases.drafts.GetDraftsUseCase
import com.example.domain.usecases.drafts.SaveDraftsUseCase
import com.example.domain.usecases.settings.GetLastAuthorizedUserUseCase
import com.example.domain.usecases.settings.GetUIModeUseCase
import com.example.domain.usecases.settings.SaveLastAuthorizedUseCase
import com.example.domain.usecases.settings.SaveUIModeUseCase
import com.example.domain.usecases.signin.CheckSignInFieldsUseCase
import com.example.domain.usecases.signin.SignInUseCase
import com.example.domain.usecases.ticket_restrictions.GetTicketCreateRestrictionsUseCase
import com.example.domain.usecases.ticket_restrictions.GetTicketDataRestrictionsUseCase
import com.example.domain.usecases.ticket_restrictions.GetTicketUpdateRestrictionsUseCase
import com.example.domain.usecases.tickets.FilterTicketsListUseCase
import com.example.domain.usecases.tickets.GetSubscriptionsUseCase
import com.example.domain.usecases.tickets.GetTicketsUseCase
import com.example.domain.usecases.tickets.SaveTicketUseCase
import com.example.domain.usecases.tickets.SubscribeUseCase
import com.example.domain.usecases.tickets.UnsubscribeUseCase
import com.example.domain.usecases.tickets.UpdateTicketUseCase
import com.example.domain.usecases.tickets_history.GetTicketsHistoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {
    // Sign in
    @Provides
    fun provideCheckSignInFieldsUseCase(): CheckSignInFieldsUseCase = CheckSignInFieldsUseCase()

    @Provides
    fun provideSignInUseCase(userRepository: IAuthRepository, checkConnectionUseCase: CheckConnectionUseCase, checkSignInFieldsUseCase: CheckSignInFieldsUseCase, saveLastAuthorizedUseCase: SaveLastAuthorizedUseCase): SignInUseCase = SignInUseCase(userRepository, checkConnectionUseCase, checkSignInFieldsUseCase, saveLastAuthorizedUseCase)

    // Tickets
    @Provides
    fun provideGetTicketsUseCase(ticketsRepository: ITicketsRepository, checkConnectionUseCase: CheckConnectionUseCase, ): GetTicketsUseCase = GetTicketsUseCase(ticketsRepository, checkConnectionUseCase)

    @Provides
    fun provideSaveTicketUseCase(ticketsRepository: ITicketsRepository, checkConnectionUseCase: CheckConnectionUseCase): SaveTicketUseCase = SaveTicketUseCase(ticketsRepository, checkConnectionUseCase)

    @Provides
    fun provideUpdateTicketUseCase(ticketsRepository: ITicketsRepository, checkConnectionUseCase: CheckConnectionUseCase, ): UpdateTicketUseCase = UpdateTicketUseCase(ticketsRepository, checkConnectionUseCase)

    @Provides
    fun provideGetTicketsHistoryUseCase(ticketsHistoryRepository: ITicketsHistoryRepository): GetTicketsHistoryUseCase = GetTicketsHistoryUseCase(ticketsHistoryRepository)

    @Provides
    fun provideSubscribeUseCase(ticketsRepository: ITicketsRepository, checkConnectionUseCase: CheckConnectionUseCase): SubscribeUseCase = SubscribeUseCase(ticketsRepository, checkConnectionUseCase)

    @Provides
    fun provideUnsubscribeUseCase(ticketsRepository: ITicketsRepository, checkConnectionUseCase: CheckConnectionUseCase): UnsubscribeUseCase = UnsubscribeUseCase(ticketsRepository, checkConnectionUseCase)

    @Provides
    fun provideGetSubscriptionsUseCase(ticketsRepository: ITicketsRepository, checkConnectionUseCase: CheckConnectionUseCase): GetSubscriptionsUseCase = GetSubscriptionsUseCase(ticketsRepository, checkConnectionUseCase)

    // Restrictions
    @Provides
    fun provideGetTicketUpdateRestrictionsUseCase(): GetTicketUpdateRestrictionsUseCase = GetTicketUpdateRestrictionsUseCase()

    @Provides
    fun provideGetTicketDataRestrictionsUseCase(): GetTicketDataRestrictionsUseCase = GetTicketDataRestrictionsUseCase()

    @Provides
    fun provideGetTicketCreateRestrictionsUseCase(): GetTicketCreateRestrictionsUseCase = GetTicketCreateRestrictionsUseCase()

    // Connections
    @Provides
    fun provideCheckConnectionUseCase(@ApplicationContext context: Context, networkConnectionRepository: INetworkConnectionRepository): CheckConnectionUseCase = CheckConnectionUseCase(networkConnectionRepository, context)

    @Provides
    fun provideGetConnectionsUseCase(connectionsDataStore: IConnectionsDataStore): GetConnectionsUseCase = GetConnectionsUseCase(connectionsDataStore)


    @Provides
    fun provideSaveConnectionsUseCase(connectionsDataStore: IConnectionsDataStore): SaveConnectionsUseCase = SaveConnectionsUseCase(connectionsDataStore)

    // Drafts
    @Provides
    fun provideGetDraftsUseCase(draftsDataStore: ITicketsDataStore): GetDraftsUseCase = GetDraftsUseCase(draftsDataStore)

    @Provides
    fun provideDeleteDraftsUseCase(draftsDataStore: ITicketsDataStore): DeleteDraftsUseCase = DeleteDraftsUseCase(draftsDataStore)

    @Provides
    fun provideSaveDraftsUseCase(draftsDataStore: ITicketsDataStore, getDraftsUseCase: GetDraftsUseCase): SaveDraftsUseCase = SaveDraftsUseCase(draftsDataStore, getDraftsUseCase)

    // Settings
    @Provides
    fun provideGetUIModeUseCase(settingsDataStore: ISettingsDataStore): GetUIModeUseCase = GetUIModeUseCase(settingsDataStore)

    @Provides
    fun provideGetLastAuthorizedUserUseCase(settingsDataStore: ISettingsDataStore): GetLastAuthorizedUserUseCase = GetLastAuthorizedUserUseCase(settingsDataStore)

    @Provides
    fun provideSaveLastAuthorizedUserUseCase(settingsDataStore: ISettingsDataStore): SaveLastAuthorizedUseCase = SaveLastAuthorizedUseCase(settingsDataStore)

    @Provides
    fun provideSaveSettingsUseCase(themeDataStore: ISettingsDataStore): SaveUIModeUseCase = SaveUIModeUseCase(themeDataStore)

    // Filtering
    @Provides
    fun provideFilterTicketsListUseCase(): FilterTicketsListUseCase = FilterTicketsListUseCase()
}