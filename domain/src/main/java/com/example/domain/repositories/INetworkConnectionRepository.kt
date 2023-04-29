package com.example.domain.repositories

import android.content.Context
import com.example.domain.enums.states.NetworkConnectionState

interface INetworkConnectionRepository {
    suspend fun checkConnection(url: String, context: Context): NetworkConnectionState
}