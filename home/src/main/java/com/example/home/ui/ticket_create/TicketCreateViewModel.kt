package com.example.home.ui.ticket_create

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.data_classes.params.CreateTicketParams
import com.example.domain.data_classes.params.TicketData
import com.example.domain.enums.states.LoadingState
import com.example.domain.enums.states.TicketCreateStates
import com.example.domain.usecases.createticket.CheckTicketUseCase
import com.example.domain.usecases.createticket.GetTicketDataUseCase
import com.example.domain.usecases.createticket.SaveTicketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class TicketCreateViewModel @Inject constructor(
    private val getTicketCreateFieldsUseCase: GetTicketDataUseCase,
    private val saveTicketUseCase: SaveTicketUseCase,
    private val checkTicketUseCase: CheckTicketUseCase,
) : ViewModel() {
    val fieldsLoadingState: MutableState<LoadingState> = mutableStateOf(LoadingState.WAIT_FOR_INIT)
    val fields: MutableState<TicketData?> = mutableStateOf(null)

    var checkResult: MutableState<List<TicketCreateStates>> = mutableStateOf(listOf())
    var saveResult: MutableState<Int> = mutableStateOf(0)

    fun initFields() {
        viewModelScope.launch(getCoroutineHandler()) {
            fieldsLoadingState.value = LoadingState.LOADING
            fields.value = getTicketCreateFieldsUseCase.execute()
            fieldsLoadingState.value = LoadingState.DONE
        }
    }

    fun offlineInitFields() {
        viewModelScope.launch {
            fieldsLoadingState.value = LoadingState.LOADING

            fieldsLoadingState.value = LoadingState.DONE
        }
    }

    private fun getCoroutineHandler() = CoroutineExceptionHandler { _, throwable ->
        if (throwable::class == ConnectException::class) {
            fieldsLoadingState.value = LoadingState.ERROR
        }
    }

    fun save(ticketEntity: CreateTicketParams) {
        viewModelScope.launch(getCoroutineHandler()) {
            saveResult.value = saveTicketUseCase.execute(ticketEntity)
        }
    }

    fun checkTicketFields(ticket: CreateTicketParams) {
        checkResult.value = checkTicketUseCase.execute(ticket)
    }
}
