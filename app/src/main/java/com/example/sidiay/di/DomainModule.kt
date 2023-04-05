package com.example.sidiay.di

import com.example.domain.repositories.IAuthRepository
import com.example.domain.repositories.IFacilityRepository
import com.example.domain.repositories.ITicketsRepository
import com.example.domain.repositories.IUserRepository
import com.example.domain.usecases.createticket.*
import com.example.domain.usecases.home.*
import com.example.domain.usecases.signin.CheckSignInFieldsUseCase
import com.example.domain.usecases.signin.SignInUseCase
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
    fun provideSaveTicketsUseCase(ticketsRepository: ITicketsRepository): SaveTicketUseCase =
        SaveTicketUseCase(ticketRepository = ticketsRepository)

    @Provides
    fun provideGetTicketCreateFieldsUseCase(
        facilitiesRepository: IFacilityRepository,
        userRepository: IUserRepository,
    ): GetTicketDataUseCase = GetTicketDataUseCase(
        facilitiesRepository = facilitiesRepository,
        userRepository = userRepository,
    )

    @Provides
    fun provideCheckTicketUseCase(): CheckTicketUseCase = CheckTicketUseCase()

    @Provides
    fun provideGetDraftsUseCase(ticketsRepository: ITicketsRepository): GetDraftsUseCase =
        GetDraftsUseCase(ticketsRepository)
}