package com.example.sidiay.presentation.viewmodels.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.enums.states.SignInStates
import com.example.domain.enums.states.TicketStates
import com.example.domain.enums.ticket.*
import com.example.domain.models.entities.TicketEntity
import com.example.domain.usecases.menu.GetTicketsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class TicketsListViewModel @Inject constructor(
    private val getTicketsUseCase: GetTicketsUseCase
) : ViewModel() {
    var tickets = MutableLiveData<List<TicketEntity>>()

    var filterList: ArrayList<TicketEntity> = arrayListOf()
    var searchList: ArrayList<TicketEntity> = arrayListOf()
    var filterSearchList: ArrayList<TicketEntity> = arrayListOf()

    var errorResult = MutableLiveData<List<TicketStates>>()

    // FILTER
    val priorities: ArrayList<TicketPriorityEnum> = TicketPriorityEnum.values().toList() as ArrayList<TicketPriorityEnum>
    val checkedPriorities: BooleanArray = getBooleanArray(priorities)

    val periods: ArrayList<TicketPeriodEnum> = TicketPeriodEnum.values().toList() as ArrayList<TicketPeriodEnum>
    val checkedPeriods: BooleanArray = getBooleanArray(periods)

    val kinds: ArrayList<TicketKindEnum> = TicketKindEnum.values().toList() as ArrayList<TicketKindEnum>
    val checkedKinds: BooleanArray = getBooleanArray(kinds)

    val services: ArrayList<TicketServiceEnum> = TicketServiceEnum.values().toList() as ArrayList<TicketServiceEnum>
    val checkedServices: BooleanArray = getBooleanArray(services)

    val statuses: ArrayList<TicketStatusEnum> = TicketStatusEnum.values().toList() as ArrayList<TicketStatusEnum>
    val checkedStatuses: BooleanArray = getBooleanArray(statuses)

    fun <T> getBooleanArray(parentArray: ArrayList<T>): BooleanArray = BooleanArray(parentArray.size)
    fun fillTicketsList() {
        viewModelScope.launch(getTicketsHandler()) {
            getTicketsUseCase.execute().second.let {
                tickets.value = it
            }
        }
    }
    private fun getTicketsHandler(): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, throwable ->
            if (throwable::class == ConnectException::class) {
                this.errorResult.value = listOf(TicketStates.NO_SERVER_CONNECTION)
            }
        }
    }
}