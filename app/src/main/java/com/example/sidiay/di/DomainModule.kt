package com.example.sidiay.di

import com.example.domain.repositories.IAuthRepository
import com.example.domain.repositories.ITicketsRepository
import com.example.domain.usecases.signin.CheckSignInFieldsUseCase
import com.example.domain.usecases.signin.SignInUseCase
import com.example.domain.usecases.tickets.GetDraftsUseCase
import com.example.domain.usecases.tickets.restrictions.GetTicketCreateRestrictionsUseCase
import com.example.domain.usecases.tickets.restrictions.GetTicketUpdateRestrictionsUseCase
import com.example.domain.usecases.tickets.GetTicketsUseCase
import com.example.domain.usecases.tickets.SaveTicketUseCase
import com.example.domain.usecases.tickets.UpdateTicketUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {
    @Provides
    fun provideCheckSignInFieldsUseCase(): CheckSignInFieldsUseCase = CheckSignInFieldsUseCase()

    @Provides
    fun provideSignInUseCase(userRepository: IAuthRepository): SignInUseCase =
        SignInUseCase(userRepository)

    @Provides
    fun provideGetTicketsUseCase(ticketsRepository: ITicketsRepository): GetTicketsUseCase =
        GetTicketsUseCase(ticketsRepository = ticketsRepository)


    @Provides
    fun provideSaveTicketUseCase(ticketsRepository: ITicketsRepository): SaveTicketUseCase =
        SaveTicketUseCase(ticketRepository = ticketsRepository)

    @Provides
    fun provideGetDraftsUseCase(ticketsRepository: ITicketsRepository): GetDraftsUseCase =
        GetDraftsUseCase(ticketsRepository)

    @Provides
    fun provideUpdateTicketUseCase(ticketsRepository: ITicketsRepository): UpdateTicketUseCase =
        UpdateTicketUseCase(ticketRepository = ticketsRepository)

    @Provides
    fun provideGetTicketUpdateRestrictionsUseCase(): GetTicketUpdateRestrictionsUseCase =
        GetTicketUpdateRestrictionsUseCase()

    @Provides
    fun provideGetTicketCreateRestrictionsUseCase(): GetTicketCreateRestrictionsUseCase =
        GetTicketCreateRestrictionsUseCase()
}