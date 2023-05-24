package com.example.data.repositories

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.core.utils.Logger
import com.example.data.network.api.ApiService
import com.example.domain.enums.states.NetworkState
import com.example.domain.repositories.INetworkConnectionRepository
import javax.inject.Inject

class NetworkConnectionRepository @Inject constructor(
    private val apiService: ApiService
) : INetworkConnectionRepository {
    override suspend fun checkConnection(url: String, context: Context): NetworkState {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            ) {
                try {
                    if (apiService.checkConnection("$url/").isSuccessful) return NetworkState.CONNECTION_ESTABLISHED
                } catch (_: Exception) {
                }

                Logger.m("No server connection")
                return NetworkState.NO_SERVER_CONNECTION
            }
        }
        Logger.m("No internet connection")
        return NetworkState.NO_SERVER_CONNECTION
    }
}