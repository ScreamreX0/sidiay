package com.example.sidiay.di

import androidx.compose.ui.unit.Constraints
import com.example.data.api.ApiService
import com.example.domain.utils.Constants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val readTimeout = 30
        val writeTimeout = 30
        val connectionTimeout = 10

        val okHttpClientBuilder = OkHttpClient().newBuilder()

        okHttpClientBuilder.connectTimeout(connectionTimeout.toLong(), TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(readTimeout.toLong(), TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(writeTimeout.toLong(), TimeUnit.SECONDS)

        return okHttpClientBuilder.build()
    }
}