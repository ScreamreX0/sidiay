package com.example.home.ui.tickets_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.utils.Helper
import com.example.core.utils.Logger
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.TicketData
import com.example.domain.enums.states.INetworkState
import com.example.domain.enums.states.NetworkState
import com.example.domain.usecases.drafts.DeleteDraftsUseCase
import com.example.domain.usecases.drafts.GetDraftsUseCase
import com.example.domain.usecases.ticket_data.GetTicketDataUseCase
import com.example.domain.usecases.ticket_data.SaveTicketDataUseCase
import com.example.domain.usecases.tickets.DeleteTicketsUseCase
import com.example.domain.usecases.tickets.GetTicketsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketsListViewModel @Inject constructor(
    // Tickets
    private val getTicketsUseCase: GetTicketsUseCase,
    private val deleteTicketsUseCase: DeleteTicketsUseCase,

    // Drafts
    private val getDraftsUseCase: GetDraftsUseCase,
    private val deleteDraftsUseCase: DeleteDraftsUseCase,

    // Ticket data
    private val getTicketDataUseCase: GetTicketDataUseCase
) : ViewModel() {
    val tickets: MutableState<List<TicketEntity>?> = mutableStateOf(null)
    val ticketsForExecution: MutableState<List<TicketEntity>?> = mutableStateOf(null)
    val ticketsPersonal: MutableState<List<TicketEntity>?> = mutableStateOf(null)
    val ticketsReceivingState: MutableState<NetworkState> = mutableStateOf(NetworkState.WAIT_FOR_INIT)

    var drafts: MutableState<List<TicketEntity>?> = mutableStateOf(null)

    val errorMessage: MutableState<INetworkState?> = mutableStateOf(null)

    val ticketData: MutableState<TicketData?> = mutableStateOf(null)
    val ticketDataFetchingResult: MutableState<INetworkState?> = mutableStateOf(null)

    fun fetchTickets(url: String?, userId: Long) {
        viewModelScope.launch(Helper.getCoroutineNetworkExceptionHandler {
            errorMessage.value = NetworkState.NO_SERVER_CONNECTION
        }) {
            ticketsReceivingState.value = NetworkState.LOADING
            val result = getTicketsUseCase.execute(url, userId)
            result.first?.let {
                Logger.m("Error: $it")
                errorMessage.value = it
                ticketsReceivingState.value = NetworkState.ERROR
            }
            result.second?.let {
                Logger.m("Tickets received")
                tickets.value = it
                ticketsForExecution.value = it.filter { ticket -> ticket.executor?.id == userId }
                ticketsPersonal.value = it.filter { ticket -> ticket.author?.id == userId }
                ticketsReceivingState.value = NetworkState.DONE
            }
        }
    }

    fun fetchDrafts() = viewModelScope.launch { drafts.value = getDraftsUseCase.execute() }

    fun fetchTicketData(url: String?) = viewModelScope.launch(
        Helper.getCoroutineNetworkExceptionHandler { ticketDataFetchingResult.value = NetworkState.NO_SERVER_CONNECTION }
    ) {
        getTicketDataUseCase.execute(url).let {
            ticketDataFetchingResult.value = NetworkState.LOADING
            ticketData.value = it.second
            ticketDataFetchingResult.value = NetworkState.DONE
        }
    }

    fun deleteDraft(ticket: TicketEntity) = viewModelScope.launch {
        deleteDraftsUseCase.execute(ticket)
        drafts.value = getDraftsUseCase.execute()
    }

    fun deleteTicket(ticket: TicketEntity) = viewModelScope.launch {
        deleteTicketsUseCase.execute(ticket)
        tickets.value = getTicketsUseCase.execute(url = null, userId = 0).second
    }
}


