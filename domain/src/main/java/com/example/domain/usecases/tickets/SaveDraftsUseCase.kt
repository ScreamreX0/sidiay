package com.example.domain.usecases.tickets

import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.repositories.IDraftsDataStore
import javax.inject.Inject

class SaveDraftsUseCase @Inject constructor(
    private val draftsDataStore: IDraftsDataStore
) {
    suspend fun execute(drafts: List<TicketEntity>) = draftsDataStore.saveDrafts(drafts)
}