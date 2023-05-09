package com.example.domain.usecases.drafts

import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.repositories.ITicketsDataStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DeleteDraftsUseCase @Inject constructor(
    private val draftsDataStore: ITicketsDataStore
) {
    suspend fun execute(ticketEntity: TicketEntity) {
        val drafts = draftsDataStore.getDrafts.first()?.toList() ?: return
        draftsDataStore.saveDrafts(drafts.minus(ticketEntity))
    }
}