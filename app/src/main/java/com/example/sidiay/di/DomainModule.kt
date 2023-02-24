package com.example.sidiay.di

import com.example.data.repositories.FacilitiesRepository
import com.example.data.repositories.UserRepository
import com.example.domain.repositories.ITicketsRepository
import com.example.domain.repositories.IUserRepository
import com.example.domain.usecases.createticket.*
import com.example.domain.usecases.home.GetTicketsUseCase
import com.example.domain.usecases.home.*
import com.example.domain.usecases.signin.CheckSignInFieldsUseCase
import com.example.domain.usecases.signin.SignInUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.intellij.lang.annotations.PrintFormat

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {
    @Provides
    fun provideCheckSignInFieldsUseCase(): CheckSignInFieldsUseCase = CheckSignInFieldsUseCase()

    @Provides
    fun provideSignInUseCase(userRepository: IUserRepository): SignInUseCase =
        SignInUseCase(userRepository)

    @Provides
    fun provideGetTicketsUseCase(applicationRepository: ITicketsRepository): GetTicketsUseCase =
        GetTicketsUseCase(ticketsRepository = applicationRepository)


    @Provides
    fun provideSaveTicketsUseCase(applicationsRepository: ITicketsRepository): SaveTicketUseCase =
        SaveTicketUseCase(ticketRepository = applicationsRepository)

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
}