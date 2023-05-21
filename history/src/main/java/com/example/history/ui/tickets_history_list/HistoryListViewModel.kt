package com.example.history.ui.tickets_history_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.utils.Helper
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.enums.states.INetworkState
import com.example.domain.enums.states.NetworkState
import com.example.domain.usecases.tickets_history.GetTicketsHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryListViewModel @Inject constructor(
    private val getTicketsHistoryUseCase: GetTicketsHistoryUseCase
) : ViewModel() {
    internal val ticketsHistoryFetchingState: MutableState<INetworkState> = mutableStateOf(NetworkState.WAIT_FOR_INIT)
    internal val ticketsHistory: MutableState<List<TicketEntity>?> = mutableStateOf(null)

    internal fun fetchTicketsHistory(url: String?, currentUserId: Long?) = viewModelScope.launch(
        Helper.getCoroutineNetworkExceptionHandler { ticketsHistoryFetchingState.value = NetworkState.NO_SERVER_CONNECTION }
    ) {
        val result = getTicketsHistoryUseCase.execute(url, currentUserId)

        if (result.first == null) {
            ticketsHistory.value = result.second
            ticketsHistoryFetchingState.value = NetworkState.DONE
        } else if (result.second == null) {
            ticketsHistoryFetchingState.value = NetworkState.ERROR
        }
    }
}