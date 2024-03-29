package com.example.home.ui.ticket_create

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.utils.Helper
import com.example.core.utils.Logger
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.TicketData
import com.example.domain.enums.states.INetworkState
import com.example.domain.enums.states.NetworkState
import com.example.domain.enums.states.TicketOperationState
import com.example.domain.usecases.drafts.SaveDraftsUseCase
import com.example.domain.usecases.ticket_data.GetTicketDataUseCase
import com.example.domain.usecases.ticket_restrictions.GetTicketCreateRestrictionsUseCase
import com.example.domain.usecases.ticket_restrictions.GetTicketDataRestrictionsUseCase
import com.example.domain.usecases.tickets.SaveTicketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketCreateViewModel @Inject constructor(
    // Tickets
    private val getTicketCreateRestrictionsUseCase: GetTicketCreateRestrictionsUseCase,
    private val saveTicketUseCase: SaveTicketUseCase,

    // Ticket data
    private val getTicketDataUseCase: GetTicketDataUseCase,
    private val getTicketDataRestrictionsUseCase: GetTicketDataRestrictionsUseCase,

    // Drafts
    private val saveDraftsUseCase: SaveDraftsUseCase
) : ViewModel() {
    val ticketDataLoadingState: MutableState<NetworkState> = mutableStateOf(NetworkState.WAIT_FOR_INIT)
    val ticketData: MutableState<TicketData?> = mutableStateOf(null)

    val savingTicketResult: MutableState<INetworkState> = mutableStateOf(TicketOperationState.WAITING)
    val savingDraftResult: MutableState<INetworkState> = mutableStateOf(TicketOperationState.WAITING)

    fun fetchTicketData(url: String?, ticket: TicketEntity, currentUser: UserEntity?) =
        viewModelScope.launch(Helper.getCoroutineNetworkExceptionHandler {
            ticketDataLoadingState.value = NetworkState.NO_SERVER_CONNECTION
        }) {
            ticketDataLoadingState.value = NetworkState.LOADING

            val result = getTicketDataUseCase.execute(url, currentUser?.id)

            result.second?.let { itTicketData ->
                Logger.m("Ticket data received")
                ticketData.value = getTicketDataRestrictionsUseCase.execute(
                    ticket = ticket,
                    currentUser = currentUser,
                    ticketData = itTicketData
                )
                ticketDataLoadingState.value = NetworkState.DONE
            } ?: run {
                Logger.e("Tickets' fields receiving error.")
                ticketDataLoadingState.value = NetworkState.ERROR
            }
        }


    fun save(url: String?, ticket: TicketEntity) = viewModelScope.launch(Helper.getCoroutineNetworkExceptionHandler {
        savingTicketResult.value = NetworkState.NO_SERVER_CONNECTION
    }) {
        savingTicketResult.value = TicketOperationState.IN_PROCESS
        val result = saveTicketUseCase.execute(url = url, ticket = ticket)

        result.first?.let {
            Logger.m("Error: ${result.second}")
            savingTicketResult.value = it
        } ?: run {
            Logger.m("Success.")
            savingTicketResult.value = TicketOperationState.DONE
        }
    }


    fun getRestrictions() = getTicketCreateRestrictionsUseCase.execute()

    fun saveDraft(drafts: TicketEntity, currentUserId: Long) = viewModelScope.launch {
        savingDraftResult.value = TicketOperationState.IN_PROCESS
        saveDraftsUseCase.execute(drafts, currentUserId)
        savingDraftResult.value = TicketOperationState.DONE
    }
}
