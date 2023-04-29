package com.example.data.repositories

import android.content.Context
import com.example.core.utils.Logger
import com.example.data.network.api.ApiService
import com.example.data.utils.NetworkUtils
import com.example.domain.enums.states.NetworkConnectionState
import com.example.domain.repositories.INetworkConnectionRepository
import javax.inject.Inject

class NetworkConnectionRepository @Inject constructor(
    private val apiService: ApiService
): INetworkConnectionRepository {
    override suspend fun checkConnection(url: String, context: Context): NetworkConnectionState {
        return if (NetworkUtils.isNetworkAvailable(context)) {
            try {
                if (apiService.checkConnection("$url/").isSuccessful) {
                    NetworkConnectionState.CONNECTION_ESTABLISHED
                } else {
                    NetworkConnectionState.NO_SERVER_CONNECTION
                }
            } catch (e: Exception) {
                NetworkConnectionState.NO_SERVER_CONNECTION
            }
        } else {
            NetworkConnectionState.NO_NETWORK_CONNECTION
        }
    }
}