package com.example.sidiay.di.auth

import com.example.signin.data.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {
    @Provides
    fun provideAuthRepository(): AuthRepository {
        return AuthRepository()
    }
}