package com.example.data.repositories

import android.content.Context
import com.example.data.network.api.ApiService
import com.example.data.utils.NetworkUtils
import com.example.domain.enums.states.NetworkState
import com.example.domain.repositories.INetworkConnectionRepository
import javax.inject.Inject

class NetworkConnectionRepository @Inject constructor(
    private val apiService: ApiService
) : INetworkConnectionRepository {
    override suspend fun checkConnection(url: String, context: Context): NetworkState {
        if (NetworkUtils.isNetworkAvailable(context)) {
            try {
                if (apiService.checkConnection("$url/").isSuccessful) return NetworkState.CONNECTION_ESTABLISHED
            } catch (_: Exception) {
            }
        }
        return NetworkState.NO_SERVER_CONNECTION
    }
}