package com.example.domain.usecases.connections

import com.example.domain.data_classes.params.ConnectionParams
import com.example.domain.repositories.IConnectionsDataStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetConnectionsUseCase @Inject constructor(
    private val connectionsDataStore: IConnectionsDataStore
) {
    suspend fun execute() = connectionsDataStore.getConnections.first()?.let { it as List<ConnectionParams> } ?: emptyList()
}