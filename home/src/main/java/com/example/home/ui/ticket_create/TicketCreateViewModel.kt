package com.example.home.ui.ticket_create

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.utils.Helper
import com.example.core.utils.Logger
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.TicketData
import com.example.domain.enums.states.LoadingState
import com.example.domain.enums.states.TicketOperationState
import com.example.domain.usecases.tickets.restrictions.GetTicketCreateRestrictionsUseCase
import com.example.domain.usecases.tickets.GetTicketDataUseCase
import com.example.domain.usecases.tickets.SaveTicketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketCreateViewModel @Inject constructor(
    private val getTicketDataUseCase: GetTicketDataUseCase,
    private val saveTicketUseCase: SaveTicketUseCase,
    private val getTicketCreateRestrictionsUseCase: GetTicketCreateRestrictionsUseCase
) : ViewModel() {
    val fieldsLoadingState: MutableState<LoadingState> = mutableStateOf(LoadingState.WAIT_FOR_INIT)
    val fields: MutableState<TicketData?> = mutableStateOf(null)
    val savingResult: MutableState<TicketOperationState> = mutableStateOf(TicketOperationState.WAITING)

    fun fetchTicketData(url: String?) {
        url?.let {
            viewModelScope.launch(Helper.getCoroutineNetworkExceptionHandler {
                fieldsLoadingState.value = LoadingState.CONNECTION_ERROR
            }) {
                fieldsLoadingState.value = LoadingState.LOADING
                val result = getTicketDataUseCase.execute(it)

                result.second?.let { ticketData ->
                    Logger.m("Ticket data received")
                    fields.value = ticketData
                    fieldsLoadingState.value = LoadingState.DONE
                } ?: run {
                    Logger.e("Tickets' fields receiving error.")
                    fieldsLoadingState.value = LoadingState.ERROR
                }
            }
        } ?: run {
            viewModelScope.launch {
                Logger.m("Getting tickets' fields offline...")
                fieldsLoadingState.value = LoadingState.LOADING
                // TODO("Add load tickets offline")
                fieldsLoadingState.value = LoadingState.DONE
            }
        }
    }

    fun save(url: String?, ticket: TicketEntity) {
        viewModelScope.launch(Helper.getCoroutineNetworkExceptionHandler {
            savingResult.value = TicketOperationState.CONNECTION_ERROR
        }) {
            savingResult.value = TicketOperationState.IN_PROCESS
            val result = saveTicketUseCase.execute(url = url, ticket = ticket)

            result.first?.let {
                Logger.m("Success.")
                savingResult.value = TicketOperationState.DONE
            } ?: run {
                Logger.m("Error: ${result.second}")
                savingResult.value = TicketOperationState.OPERATION_ERROR
            }
        }
    }

    fun getRestrictions() = getTicketCreateRestrictionsUseCase.execute()
}
