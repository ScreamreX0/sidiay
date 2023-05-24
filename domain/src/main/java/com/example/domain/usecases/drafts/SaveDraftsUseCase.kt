package com.example.domain.usecases.drafts

import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.repositories.ITicketsDataStore
import javax.inject.Inject

class SaveDraftsUseCase @Inject constructor(
    private val draftsDataStore: ITicketsDataStore,
    private val getDraftsUseCase: GetDraftsUseCase,
) {
    suspend fun execute(draft: TicketEntity) = draftsDataStore.saveDrafts(getDraftsUseCase.execute()?.plus(draft) ?: listOf(draft))
}