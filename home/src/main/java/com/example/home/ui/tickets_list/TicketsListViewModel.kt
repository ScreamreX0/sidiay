package com.example.home.ui.tickets_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.utils.Helper
import com.example.core.utils.Logger
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.enums.states.INetworkState
import com.example.domain.enums.states.NetworkState
import com.example.domain.usecases.tickets.GetTicketsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketsListViewModel @Inject constructor(
    private val getTicketsUseCase: GetTicketsUseCase,
) : ViewModel() {
    val ticketsReceivingState = mutableStateOf(NetworkState.WAIT_FOR_INIT)
    val ticketsForExecution: MutableState<List<TicketEntity>?> = mutableStateOf(null)
    val ticketsPersonal: MutableState<List<TicketEntity>?> = mutableStateOf(null)
    val errorMessage: MutableState<INetworkState?> = mutableStateOf(null)
    var drafts = mutableStateOf<List<TicketEntity>>(listOf())

    fun fetchTickets(url: String?, userId: Long) {
        url?.let { currentUrl ->
            viewModelScope.launch(Helper.getCoroutineNetworkExceptionHandler {
                errorMessage.value = NetworkState.NO_SERVER_CONNECTION
            }) {
                ticketsReceivingState.value = NetworkState.LOADING
                val result = getTicketsUseCase.execute(currentUrl, userId)
                result.first?.let {
                    Logger.m("Error: $it")
                    errorMessage.value = it
                    ticketsReceivingState.value = NetworkState.ERROR
                }
                result.second?.let {
                    Logger.m("Tickets received")
                    ticketsForExecution.value = it.filter { ticket -> ticket.executor?.id == userId }
                    ticketsPersonal.value = it.filter { ticket -> ticket.author?.id == userId }
                    ticketsReceivingState.value = NetworkState.DONE
                }
            }
        } ?: run {
            Logger.m("Getting tickets offline...")
            ticketsReceivingState.value = NetworkState.LOADING
            // TODO("Add offline mode")
            ticketsReceivingState.value = NetworkState.DONE
        }
    }
}


