package com.example.domain.usecases.ticket_data

import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.repositories.ITicketsDataStore
import javax.inject.Inject

class SaveTicketDataUseCase @Inject constructor(
    private val draftsDataStore: ITicketsDataStore
) {
    suspend fun execute(drafts: List<TicketEntity>) = draftsDataStore.saveDrafts(drafts)
}