package com.example.home.ui.ticket_create

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.utils.Logger
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.TicketData
import com.example.domain.enums.states.LoadingState
import com.example.domain.enums.states.TicketCreateStates
import com.example.domain.usecases.createticket.ValidateTicketUseCase
import com.example.domain.usecases.createticket.GetTicketDataUseCase
import com.example.domain.usecases.createticket.SaveTicketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class TicketCreateViewModel @Inject constructor(
    private val getTicketDataUseCase: GetTicketDataUseCase,
    private val saveTicketUseCase: SaveTicketUseCase,
    private val validateTicketUseCase: ValidateTicketUseCase,
) : ViewModel() {
    val fieldsLoadingState: MutableState<LoadingState> = mutableStateOf(LoadingState.WAIT_FOR_INIT)
    val fields: MutableState<TicketData?> = mutableStateOf(null)

    var checkResult: MutableState<List<TicketCreateStates>> = mutableStateOf(listOf())
    var saveResult: MutableState<String?> = mutableStateOf(null)

    fun initFields(url: String?) {
        Logger.m("Check network mode...")
        url?.let {
            viewModelScope.launch(getCoroutineHandler()) {
                Logger.m("Getting tickets' fields online...")
                fieldsLoadingState.value = LoadingState.LOADING
                val result = getTicketDataUseCase.execute(it)

                result.first?.let { itData ->
                    Logger.m("Tickets' fields received.")
                    fields.value = itData
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
        viewModelScope.launch(getCoroutineHandler()) {
            validateTicketFields(ticket)

            Logger.m("Trying to save new ticket...")
            val result = saveTicketUseCase.execute(url = url, ticket = ticket)

            result.first?.let {
                Logger.m("Success.")
                saveResult.value = "Заявка успешно сохранена"
            } ?: run {
                Logger.m("Error: ${result.second}")
                saveResult.value = result.second
            }
        }
    }

    private fun validateTicketFields(ticket: TicketEntity) {
        Logger.m("Validating ticket fields...")
        checkResult.value = validateTicketUseCase.execute(ticket)
    }

    private fun getCoroutineHandler() = CoroutineExceptionHandler { _, throwable ->
        if (throwable::class == ConnectException::class) {
            fieldsLoadingState.value = LoadingState.CONNECTION_ERROR
        }
    }
}
