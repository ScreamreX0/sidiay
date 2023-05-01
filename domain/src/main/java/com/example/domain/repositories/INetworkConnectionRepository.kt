package com.example.domain.repositories

import android.content.Context
import com.example.domain.enums.states.NetworkState

interface INetworkConnectionRepository {
    suspend fun checkConnection(url: String, context: Context): NetworkState
}