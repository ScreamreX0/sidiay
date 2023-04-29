package com.example.domain.usecases.connections

import com.example.domain.data_classes.params.ConnectionParams
import com.example.domain.repositories.IConnectionsDataStore
import javax.inject.Inject

class SaveConnectionsUseCase @Inject constructor(
    private val connectionsDataStore: IConnectionsDataStore
) {
    suspend fun execute(connectionsList: List<ConnectionParams>) = connectionsDataStore.saveConnections(connectionsList)
}