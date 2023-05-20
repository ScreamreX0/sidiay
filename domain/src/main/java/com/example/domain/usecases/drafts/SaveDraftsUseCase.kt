package com.example.domain.usecases.drafts

import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.repositories.ITicketsDataStore
import com.example.domain.usecases.settings.GetLastAuthorizedUserUseCase
import javax.inject.Inject

class SaveDraftsUseCase @Inject constructor(
    private val draftsDataStore: ITicketsDataStore,
    private val getDraftsUseCase: GetDraftsUseCase,
) {
    suspend fun execute(draft: TicketEntity, currentUser: UserEntity) {
        draft.author?.let {
            draftsDataStore.saveDrafts(getDraftsUseCase.execute()?.plus(draft) ?: listOf(draft))
        } ?: run {
            draftsDataStore.saveDrafts(getDraftsUseCase.execute()?.plus(draft.copy(author = currentUser)) ?: listOf(draft))
        }
    }
}