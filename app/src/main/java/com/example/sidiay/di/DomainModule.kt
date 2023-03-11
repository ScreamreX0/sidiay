package com.example.sidiay.di

import com.example.data.repositories.FacilitiesRepository
import com.example.data.repositories.UserRepository
import com.example.domain.repositories.IAuthRepository
import com.example.domain.repositories.ITicketsRepository
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
    fun provideGetServicesUseCase(): GetServicesUseCase = GetServicesUseCase()

    @Provides
    fun provideGetKindsUseCase(): GetKindsUseCase = GetKindsUseCase()

    @Provides
    fun provideGetPrioritiesUseCase(): GetPrioritiesUseCase = GetPrioritiesUseCase()

    @Provides
    fun provideGetPeriodsUseCase(): GetPeriodsUseCase = GetPeriodsUseCase()

    @Provides
    fun provideGetStatusesUseCase(): GetStatusesUseCase = GetStatusesUseCase()

    @Provides
    fun provideGetUsersUseCase(userRepository: UserRepository): GetUsersUseCase =
        GetUsersUseCase(userRepository = userRepository)

    @Provides
    fun provideGetObjectsUseCase(facilitiesRepository: FacilitiesRepository): GetFacilitiesUseCase =
        GetFacilitiesUseCase(facilitiesRepository = facilitiesRepository)

    @Provides
    fun provideCheckTicketUseCase(): CheckTicketUseCase = CheckTicketUseCase()

    @Provides
    fun provideGetDraftsUseCase(ticketsRepository: ITicketsRepository): GetDraftsUseCase =
        GetDraftsUseCase(ticketsRepository)
}