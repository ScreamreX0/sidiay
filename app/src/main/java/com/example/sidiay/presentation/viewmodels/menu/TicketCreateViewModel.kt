package com.example.sidiay.presentation.viewmodels.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.enums.ticket.TicketPriorityEnum
import com.example.domain.enums.ticket.TicketServiceEnum
import com.example.domain.enums.states.TicketStates
import com.example.domain.enums.ticket.TicketKindEnum
import com.example.domain.enums.ticket.TicketStatusEnum
import com.example.domain.models.entities.FacilityEntity
import com.example.domain.models.entities.UserEntity
import com.example.domain.models.params.AddTicketParams
import com.example.domain.usecases.createticket.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class TicketCreateViewModel @Inject constructor(
    private val getServicesUseCase: GetServicesUseCase,
    private val getKindsUseCase: GetKindsUseCase,
    private val getStatusesUseCase: GetStatusesUseCase,
    private val getPrioritiesUseCase: GetPrioritiesUseCase,
    private val getFacilitiesUseCase: GetFacilitiesUseCase,
    private val getUsersUseCase: GetUsersUseCase,
    private val saveTicketUseCase: SaveTicketUseCase
) : ViewModel() {
    val ticketServiceEnum: MutableLiveData<List<TicketServiceEnum>> = MutableLiveData()
    val ticketKindEnum: MutableLiveData<List<TicketKindEnum>> = MutableLiveData()
    val statuses: MutableLiveData<List<TicketStatusEnum>> = MutableLiveData()
    val ticketPriorityEnum: MutableLiveData<List<TicketPriorityEnum>> = MutableLiveData()

    // Suspend vars
    val facilities: MutableLiveData<List<FacilityEntity>> = MutableLiveData()
    val employees: MutableLiveData<List<UserEntity>> = MutableLiveData()

    var errorsList: MutableLiveData<List<TicketStates>> = MutableLiveData()

    var saveResult: MutableLiveData<Int> = MutableLiveData()

    fun save(ticketEntity: AddTicketParams) {
        viewModelScope.launch(getConnectionHandler()) {
            saveResult.value = saveTicketUseCase.execute(ticketEntity)
        }

    }

    fun initServices() {
        ticketServiceEnum.value = getServicesUseCase.execute()
    }

    fun initKinds() {
        ticketKindEnum.value = getKindsUseCase.execute()
    }

    fun initStatuses() {
        statuses.value = getStatusesUseCase.execute()
    }

    fun initPriorities() {
        ticketPriorityEnum.value = getPrioritiesUseCase.execute()
    }

    fun initFacilities() {
        viewModelScope.launch(getConnectionHandler()) {
            getFacilitiesUseCase.execute()?.let {
                facilities.value = it
            }
        }
    }

    fun initEmployees() {
        viewModelScope.launch(getConnectionHandler()) {
            getUsersUseCase.execute()?.let {
                employees.value = it
            }
        }
    }

    private fun getConnectionHandler(): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, throwable ->
            if (throwable::class == ConnectException::class) {
                errorsList.value = listOf(TicketStates.NO_SERVER_CONNECTION)
            }
        }
    }
}