package com.example.home.ui.tickets_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.utils.Helper
import com.example.core.utils.Logger
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.FilteringParams
import com.example.domain.data_classes.params.SortingParams
import com.example.domain.data_classes.params.TicketData
import com.example.domain.enums.states.INetworkState
import com.example.domain.enums.states.NetworkState
import com.example.domain.usecases.drafts.DeleteDraftsUseCase
import com.example.domain.usecases.drafts.GetDraftsUseCase
import com.example.domain.usecases.ticket_data.GetTicketDataUseCase
import com.example.domain.usecases.tickets.FilterTicketsListUseCase
import com.example.domain.usecases.tickets.GetTicketsUseCase
import com.example.domain.usecases.tickets.SubscribeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketsListViewModel @Inject constructor(
    // Tickets
    private val getTicketsUseCase: GetTicketsUseCase,
    private val filterTicketsListUseCase: FilterTicketsListUseCase,
    private val subscribeUseCase: SubscribeUseCase,

    // Drafts
    private val getDraftsUseCase: GetDraftsUseCase,
    private val deleteDraftsUseCase: DeleteDraftsUseCase,

    // Ticket data
    private val getTicketDataUseCase: GetTicketDataUseCase
) : ViewModel() {
    private val tickets: MutableState<List<TicketEntity>?> = mutableStateOf(null)
    private val filteredTickets: MutableState<List<TicketEntity>?> = mutableStateOf(null)
    val filteredAndSearchedTickets: MutableState<List<TicketEntity>?> = mutableStateOf(null)
    val ticketsReceivingState: MutableState<NetworkState> = mutableStateOf(NetworkState.WAIT_FOR_INIT)

    var drafts: MutableState<List<TicketEntity>?> = mutableStateOf(null)
    val errorMessage: MutableState<INetworkState?> = mutableStateOf(null)
    val ticketData: MutableState<TicketData?> = mutableStateOf(null)

    fun fetchTickets(
        url: String?,
        userId: Long,
        filteringParams: FilteringParams?,
        sortingParams: SortingParams?,
        searchText: TextFieldValue
    ) {
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
                filterTickets(filteringParams, sortingParams, searchText)
                ticketsReceivingState.value = NetworkState.DONE
            }
        }
    }

    fun filterTickets(
        filteringParams: FilteringParams?,
        sortingParams: SortingParams?,
        searchText: TextFieldValue
    ) = viewModelScope.launch {
        filteredTickets.value = filterTicketsListUseCase.execute(filteringParams, sortingParams, tickets.value)
        searchTickets(searchText)
    }

    fun searchTickets(searchText: TextFieldValue) = viewModelScope.launch {
        if (searchText.text.isNotBlank()) {
            filteredAndSearchedTickets.value = filteredTickets.value?.filter { it.ticket_name?.contains(searchText.text) ?: false }
        } else {
            filteredAndSearchedTickets.value = filteredTickets.value
        }
    }

    fun subscribeToTicket(url: String?, currentUserId: Long?, ticketId: Long) = viewModelScope.launch {
        subscribeUseCase.execute(url, currentUserId, ticketId)
    }

    // Drafts
    fun fetchDrafts() = viewModelScope.launch { drafts.value = getDraftsUseCase.execute() }

    fun deleteDraft(ticket: TicketEntity) = viewModelScope.launch {
        deleteDraftsUseCase.execute(ticket)
        drafts.value = getDraftsUseCase.execute()
    }

    // Ticket data
    fun fetchTicketData(url: String?, currentUserId: Long?) = viewModelScope.launch {
        getTicketDataUseCase.execute(url, currentUserId).let { ticketData.value = it.second }
    }
}


