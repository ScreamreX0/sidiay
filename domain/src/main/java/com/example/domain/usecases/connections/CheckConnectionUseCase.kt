package com.example.domain.usecases.connections

import android.content.Context
import com.example.domain.enums.states.NetworkState
import com.example.domain.repositories.INetworkConnectionRepository
import javax.inject.Inject

class CheckConnectionUseCase @Inject constructor(
    private val networkConnectionRepository: INetworkConnectionRepository,
    private val context: Context
) {
    suspend fun execute(url: String?): NetworkState {
        if (url == null) return NetworkState.NO_SERVER_CONNECTION

        if (url.last().toString() != "/") {
            url.plus("/")
        }

        return networkConnectionRepository.checkConnection(url, context)
    }
}