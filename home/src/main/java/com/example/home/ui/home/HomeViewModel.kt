package com.example.home.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.utils.Logger
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.enums.states.LoadingState
import com.example.domain.usecases.tickets.GetTicketsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTicketsUseCase: GetTicketsUseCase,
) : ViewModel() {
    val ticketsLoadingState = mutableStateOf(LoadingState.WAIT_FOR_INIT)
    var tickets = mutableStateOf<List<TicketEntity>>(listOf())
    var drafts = mutableStateOf<List<TicketEntity>>(listOf())
    var applicationReceivingErrors = mutableStateOf<String?>(null)

    fun getTickets(url: String?, userId: Long) {
        Logger.m("Check network mode...")
        url?.let { currentUrl ->
            viewModelScope.launch(getTicketsHandler()) {
                Logger.m("Getting tickets online with userid: $userId")
                ticketsLoadingState.value = LoadingState.LOADING
                val result = getTicketsUseCase.execute(currentUrl, userId)

                result.first?.let {
                    Logger.m("Tickets received.")
                    tickets.value = it
                    ticketsLoadingState.value = LoadingState.DONE
                } ?: run {
                    Logger.e("Tickets receiving error.")
                    ticketsLoadingState.value = LoadingState.ERROR
                    applicationReceivingErrors.value = result.second
                }
            }
        } ?: run {
            Logger.m("Getting tickets offline...")
            ticketsLoadingState.value = LoadingState.LOADING
            // TODO("Add handler if url is null")
        }
    }

    private fun getTicketsHandler(): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, throwable ->
            if (throwable::class == ConnectException::class) {
                applicationReceivingErrors.value = "Нет подключения к сети"
            }
        }
    }
}


