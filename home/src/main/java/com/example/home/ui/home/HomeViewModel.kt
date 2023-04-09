package com.example.home.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.utils.Logger
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.usecases.home.GetTicketsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTicketsUseCase: GetTicketsUseCase,
) : ViewModel() {
    var tickets = mutableStateOf<List<TicketEntity>>(listOf())
    var drafts = mutableStateOf<List<TicketEntity>>(listOf())
    var applicationReceivingErrors = mutableStateOf<String?>(null)

    fun getTickets(url: String?) {
        Logger.m("Check network mode...")
        url?.let { itUrl ->
            viewModelScope.launch(getTicketsHandler()) {
                Logger.m("Getting tickets online...")
                val result = getTicketsUseCase.execute(itUrl)

                result.first?.let {
                    Logger.m("Tickets received.")
                    tickets.value = it
                } ?: run {
                    Logger.e("Tickets receiving error.")
                    applicationReceivingErrors.value = result.second
                }
            }
        } ?: run {
            Logger.m("Getting tickets offline...")
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


