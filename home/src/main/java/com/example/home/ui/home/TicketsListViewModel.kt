package com.example.home.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.enums.states.TicketCreateStates
import com.example.domain.models.entities.TicketEntity
import com.example.domain.usecases.home.GetTicketsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class TicketsListViewModel @Inject constructor(
    private val getTicketsUseCase: GetTicketsUseCase,
    //private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    var tickets = mutableStateOf<List<TicketEntity>>(listOf())
//
//    var filterList: ArrayList<TicketEntity> = arrayListOf()
//    var searchList: ArrayList<TicketEntity> = arrayListOf()
//    var filterSearchList: ArrayList<TicketEntity> = arrayListOf()
//
    var errorResult = mutableStateOf<List<TicketCreateStates>>(listOf())
//
//    // FILTER
//    val priorities: ArrayList<TicketPriorityEnum> =
//        TicketPriorityEnum.values().toList() as ArrayList<TicketPriorityEnum>
//    val checkedPriorities: BooleanArray = getBooleanArray(priorities)
//
//    val periods: ArrayList<TicketPeriodEnum> =
//        TicketPeriodEnum.values().toList() as ArrayList<TicketPeriodEnum>
//    val checkedPeriods: BooleanArray = getBooleanArray(periods)
//
//    val kinds: ArrayList<TicketKindEnum> =
//        TicketKindEnum.values().toList() as ArrayList<TicketKindEnum>
//    val checkedKinds: BooleanArray = getBooleanArray(kinds)
//
//    val services: ArrayList<TicketServiceEnum> =
//        TicketServiceEnum.values().toList() as ArrayList<TicketServiceEnum>
//    val checkedServices: BooleanArray = getBooleanArray(services)
//
//    val statuses: ArrayList<TicketStatusEnum> =
//        TicketStatusEnum.values().toList() as ArrayList<TicketStatusEnum>
//    val checkedStatuses: BooleanArray = getBooleanArray(statuses)

//    private val name: String? = savedStateHandle["userName"]
//    fun testGettingsArgs() {
//        println(name)
//        name?.let {
//            Debugger.printInfo(it)
//        }
//    }

    init {
        viewModelScope.launch(getTicketsHandler()) {
            getTicketsUseCase.execute().second?.let {
                tickets.value = it
            }
        }
    }

    private fun getTicketsHandler(): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, throwable ->
            if (throwable::class == ConnectException::class) {
                this.errorResult.value = listOf(TicketCreateStates.NO_SERVER_CONNECTION)
            }
        }
    }
}


