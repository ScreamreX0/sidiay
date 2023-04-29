package com.example.domain.usecases.signin

import android.content.Context
import com.example.domain.enums.states.NetworkConnectionState
import com.example.domain.repositories.IAuthRepository
import com.example.domain.repositories.INetworkConnectionRepository
import javax.inject.Inject

class CheckConnectionUseCase @Inject constructor(
    private val networkConnectionRepository: INetworkConnectionRepository
) {
    suspend fun execute(url: String?, context: Context): NetworkConnectionState {
        if (url == null) return NetworkConnectionState.NO_SERVER_CONNECTION

        if (url.last().toString() != "/") {
            url.plus("/")
        }

        return networkConnectionRepository.checkConnection(url, context)
    }
}