package com.example.domain.repositories

import com.example.domain.data_classes.params.ConnectionParams
import kotlinx.coroutines.flow.Flow

interface IConnectionsDataStore {
    val getConnections: Flow<Collection<ConnectionParams>?>
    suspend fun saveConnections(connections: Collection<ConnectionParams>)
}