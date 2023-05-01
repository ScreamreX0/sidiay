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
import com.example.domain.usecases.tickets.GetTicketDataUseCase
import com.example.domain.usecases.tickets.restrictions.GetTicketUpdateRestrictionsUseCase
import com.example.domain.usecases.tickets.UpdateTicketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketUpdateViewModel @Inject constructor(
    private val getTicketDataUseCase: GetTicketDataUseCase,
    private val updateTicketUseCase: UpdateTicketUseCase,
    private val getTicketRestrictionsUseCase: GetTicketUpdateRestrictionsUseCase
) : ViewModel() {
    val fieldsLoadingState: MutableState<INetworkState> = mutableStateOf(NetworkState.WAIT_FOR_INIT)
    val fields: MutableState<TicketData?> = mutableStateOf(null)

    var updatingResult: MutableState<INetworkState> = mutableStateOf(TicketOperationState.WAITING)
    val updatingMessage: MutableState<String?> = mutableStateOf(null, neverEqualPolicy())
    val restrictions: MutableState<TicketRestriction> = mutableStateOf(TicketRestriction.getEmpty())

    fun initFields(url: String?) {
        Logger.m("Check network mode...")
        url?.let {
            viewModelScope.launch(Helper.getCoroutineNetworkExceptionHandler {
                fieldsLoadingState.value = NetworkState.NO_SERVER_CONNECTION
            }) {
                Logger.m("Getting tickets' fields online...")
                fieldsLoadingState.value = NetworkState.LOADING
                val result = getTicketDataUseCase.execute(it)

                result.second?.let { itData ->
                    Logger.m("Ticket fields received.")
                    fields.value = itData
                    fieldsLoadingState.value = NetworkState.DONE
                } ?: run {
                    Logger.e("Ticket fields receiving error.")
                    fieldsLoadingState.value = NetworkState.ERROR
                }
            }
        } ?: run {
            viewModelScope.launch {
                Logger.m("Getting tickets' fields offline...")
                fieldsLoadingState.value = NetworkState.LOADING
                // TODO("Add load tickets offline")
                fieldsLoadingState.value = NetworkState.DONE
            }
        }
    }

    fun update(ticket: TicketEntity, authParams: AuthParams) {
        viewModelScope.launch(Helper.getCoroutineNetworkExceptionHandler {
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
    }

    fun initRestrictions(selectedTicketStatus: TicketStatuses, ticket: TicketEntity, currentUser: UserEntity?) {
        viewModelScope.launch {
            restrictions.value = getTicketRestrictionsUseCase.execute(
                selectedTicketStatus = selectedTicketStatus,
                ticket = ticket,
                currentUser = currentUser
            )
        }
    }
}