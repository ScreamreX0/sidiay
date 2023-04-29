package com.example.domain.usecases.tickets

import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.repositories.IDraftsDataStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetDraftsUseCase @Inject constructor(
    private val draftsDataStore: IDraftsDataStore
) {
    suspend fun execute() = draftsDataStore.getDrafts.first()?.let { it as List<TicketEntity> } ?: emptyList()
}