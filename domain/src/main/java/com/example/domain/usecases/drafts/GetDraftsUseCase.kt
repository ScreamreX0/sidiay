package com.example.domain.usecases.drafts

import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.repositories.ITicketsDataStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetDraftsUseCase @Inject constructor(
    private val draftsDataStore: ITicketsDataStore
) {
    suspend fun execute(currentUserId: Long) = draftsDataStore.getDrafts.first()?.let{
        it as List<TicketEntity> }?.filter { it.author?.id == currentUserId } ?: listOf()
}