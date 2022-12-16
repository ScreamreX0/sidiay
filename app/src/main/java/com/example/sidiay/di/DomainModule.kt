package com.example.sidiay.di

import com.example.data.repositories.ObjectsRepository
import com.example.data.repositories.UserRepository
import com.example.domain.repositories.IApplicationRepository
import com.example.domain.repositories.IUserRepository
import com.example.domain.usecases.menu.GetApplicationUseCase
import com.example.domain.usecases.menu.GetApplicationsUseCase
import com.example.domain.usecases.menu.create.*
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
    fun provideGetApplicationsUseCase(applicationRepository: IApplicationRepository): GetApplicationsUseCase {
        return GetApplicationsUseCase(applicationRepository = applicationRepository)
    }

    @Provides
    fun provideGetApplicationUseCase(applicationRepository: IApplicationRepository): GetApplicationUseCase {
        return GetApplicationUseCase(applicationRepository = applicationRepository)
    }

    @Provides
    fun provideSaveApplicationUseCase(applicationsRepository: IApplicationRepository): SaveApplicationUseCase {
        return SaveApplicationUseCase(applicationRepository = applicationsRepository)
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
    fun provideGetObjectsUseCase(objectsRepository: ObjectsRepository): GetObjectsUseCase {
        return GetObjectsUseCase(objectsRepository = objectsRepository)
    }
}