package com.example.subscriptions.ui.subscriptions_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.utils.Helper
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.enums.states.INetworkState
import com.example.domain.enums.states.NetworkState
import com.example.domain.usecases.tickets.GetSubscriptionsUseCase
import com.example.domain.usecases.tickets.UnsubscribeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscriptionsViewModel @Inject constructor(
    private val getSubscriptionsUseCase: GetSubscriptionsUseCase,
    private val unsubscribeUseCase: UnsubscribeUseCase
) : ViewModel() {
    internal val notificationsFetchingState: MutableState<INetworkState> = mutableStateOf(NetworkState.WAIT_FOR_INIT)
    internal val notifications: MutableState<List<TicketEntity>?> = mutableStateOf(null)

    internal fun fetchNotifications(url: String?, currentUserId: Long?) = viewModelScope.launch(
        Helper.getCoroutineNetworkExceptionHandler { notificationsFetchingState.value = NetworkState.NO_SERVER_CONNECTION }
    ) {
        val result = getSubscriptionsUseCase.execute(url, currentUserId)

        if (result.first == null) {
            notifications.value = result.second
            notificationsFetchingState.value = NetworkState.DONE
        } else if (result.second == null) {
            notificationsFetchingState.value = NetworkState.ERROR
        }
    }

    internal fun unsubscribe(url: String?, currentUserId: Long?, ticket: TicketEntity) = viewModelScope.launch {
        unsubscribeUseCase.execute(url, currentUserId, ticket.id)
        fetchNotifications(url, currentUserId)
    }
}