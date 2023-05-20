package com.example.home.ui.ticket_update

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.utils.Helper
import com.example.core.utils.Logger
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.AuthParams
import com.example.domain.data_classes.params.TicketData
import com.example.domain.data_classes.params.TicketRestriction
import com.example.domain.enums.TicketStatuses
import com.example.domain.enums.states.INetworkState
import com.example.domain.enums.states.NetworkState
import com.example.domain.enums.states.TicketOperationState
import com.example.domain.usecases.drafts.SaveDraftsUseCase
import com.example.domain.usecases.ticket_data.GetTicketDataUseCase
import com.example.domain.usecases.ticket_restrictions.GetTicketDataRestrictionsUseCase
import com.example.domain.usecases.ticket_restrictions.GetTicketUpdateRestrictionsUseCase
import com.example.domain.usecases.tickets.UpdateTicketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketUpdateViewModel @Inject constructor(
    // Ticket data
    private val getTicketDataRestrictionsUseCase: GetTicketDataRestrictionsUseCase,
    private val getTicketDataUseCase: GetTicketDataUseCase,

    // Tickets
    private val updateTicketUseCase: UpdateTicketUseCase,
    private val getTicketRestrictionsUseCase: GetTicketUpdateRestrictionsUseCase,

    // Drafts
    private val saveDraftsUseCase: SaveDraftsUseCase,
) : ViewModel() {
    val ticketDataLoadingState: MutableState<INetworkState> = mutableStateOf(NetworkState.WAIT_FOR_INIT)
    val ticketData: MutableState<TicketData?> = mutableStateOf(null)

    var updatingResult: MutableState<INetworkState> = mutableStateOf(TicketOperationState.WAITING)
    val updatingMessage: MutableState<String?> = mutableStateOf(null, neverEqualPolicy())
    val restrictions: MutableState<TicketRestriction> = mutableStateOf(TicketRestriction.getEmpty())

    val savingDraftResult: MutableState<INetworkState> = mutableStateOf(TicketOperationState.WAITING)

    fun fetchTicketData(url: String?, ticket: TicketEntity, currentUser: UserEntity?) =
        viewModelScope.launch(Helper.getCoroutineNetworkExceptionHandler {
            ticketDataLoadingState.value = NetworkState.NO_SERVER_CONNECTION
        }) {
            Logger.m("Getting tickets' fields online...")
            ticketDataLoadingState.value = NetworkState.LOADING
            val result = getTicketDataUseCase.execute(url)

            result.second?.let { itTicketData ->
                Logger.m("Ticket fields received.")
                ticketData.value = getTicketDataRestrictionsUseCase.execute(
                    ticket = ticket,
                    currentUser = currentUser,
                    ticketData = itTicketData
                )
                ticketDataLoadingState.value = NetworkState.DONE
            } ?: run {
                Logger.e("Ticket fields receiving error.")
                ticketDataLoadingState.value = NetworkState.ERROR
            }
        }

    fun update(ticket: TicketEntity, authParams: AuthParams) = viewModelScope.launch(Helper.getCoroutineNetworkExceptionHandler {
        updatingResult.value = NetworkState.NO_SERVER_CONNECTION
    }) {
        Logger.m("Trying to update new ticket...")
        updatingResult.value = TicketOperationState.IN_PROCESS
        val result = updateTicketUseCase.execute(
            url = authParams.connectionParams?.url,
            currentUserId = authParams.user?.id,
            ticket = ticket
        )
        result.first?.let {
            Logger.m("Error: ${result.second}")
            updatingMessage.value = it.toString()
            updatingResult.value = TicketOperationState.ERROR
        } ?: run {
            Logger.m("Success.")
            updatingResult.value = TicketOperationState.DONE
        }
    }


    fun saveDraft(draft: TicketEntity, currentUser: UserEntity) = viewModelScope.launch {
        savingDraftResult.value = TicketOperationState.IN_PROCESS
        saveDraftsUseCase.execute(draft, currentUser)
        savingDraftResult.value = TicketOperationState.DONE
    }

    fun fetchRestrictions(selectedTicketStatus: TicketStatuses, ticket: TicketEntity, currentUser: UserEntity?) {
        viewModelScope.launch {
            restrictions.value = getTicketRestrictionsUseCase.execute(
                selectedTicketStatus = selectedTicketStatus,
                ticket = ticket,
                currentUser = currentUser
            )
        }
    }
}