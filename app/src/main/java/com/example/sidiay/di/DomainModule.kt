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

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {
    @Provides
    fun provideCheckSignInFieldsUseCase(): CheckSignInFieldsUseCase {
        return CheckSignInFieldsUseCase()
    }

    @Provides
    fun provideSignInUseCase(userRepository: IUserRepository): SignInUseCase {
        return SignInUseCase(userRepository)
    }

    @Provides
    fun provideGetTicketsUseCase(applicationRepository: ITicketsRepository): GetTicketsUseCase {
        return GetTicketsUseCase(ticketsRepository = applicationRepository)
    }

    @Provides
    fun provideSaveTicketsUseCase(applicationsRepository: ITicketsRepository): SaveTicketUseCase {
        return SaveTicketUseCase(ticketRepository = applicationsRepository)
    }

    @Provides
    fun provideGetServicesUseCase(): GetServicesUseCase {
        return GetServicesUseCase()
    }

    @Provides
    fun provideGetKindsUseCase(): GetKindsUseCase {
        return GetKindsUseCase()
    }

    @Provides
    fun provideGetPrioritiesUseCase(): GetPrioritiesUseCase {
        return GetPrioritiesUseCase()
    }

    @Provides
    fun provideGetPeriodsUseCase(): GetPeriodsUseCase {
        return GetPeriodsUseCase()
    }

    @Provides
    fun provideGetStatusesUseCase(): GetStatusesUseCase {
        return GetStatusesUseCase()
    }

    @Provides
    fun provideGetUsersUseCase(userRepository: UserRepository): GetUsersUseCase {
        return GetUsersUseCase(userRepository = userRepository)
    }

    @Provides
    fun provideGetObjectsUseCase(facilitiesRepository: FacilitiesRepository): GetFacilitiesUseCase {
        return GetFacilitiesUseCase(facilitiesRepository = facilitiesRepository)
    }
}