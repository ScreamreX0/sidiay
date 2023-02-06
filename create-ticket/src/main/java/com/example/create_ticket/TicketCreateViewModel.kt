package com.example.create_ticket

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
    private val saveTicketUseCase: SaveTicketUseCase,
    private val checkTicketUseCase: CheckTicketUseCase
) : ViewModel() {
    val mutableServices: MutableLiveData<List<TicketServiceEnum>> = MutableLiveData()
    val mutableKinds: MutableLiveData<List<TicketKindEnum>> = MutableLiveData()
    val mutableStatuses: MutableLiveData<List<TicketStatusEnum>> = MutableLiveData()
    val mutablePriorities: MutableLiveData<List<TicketPriorityEnum>> = MutableLiveData()

    // Suspend vars
    val mutableFacilities: MutableLiveData<List<FacilityEntity>> = MutableLiveData()
    val mutableEmployees: MutableLiveData<List<UserEntity>> = MutableLiveData()

    var mutableErrors: MutableLiveData<List<TicketStates>> = MutableLiveData()

    var mutableSaveResult: MutableLiveData<Int> = MutableLiveData()

    var mutableFieldsCheckResult: MutableLiveData<List<TicketStates>> = MutableLiveData()

    fun save(ticketEntity: AddTicketParams) {
        viewModelScope.launch(getConnectionHandler()) {
            mutableSaveResult.value = saveTicketUseCase.execute(ticketEntity)
        }

    }

    fun initServices() {
        mutableServices.value = getServicesUseCase.execute()
    }

    fun initKinds() {
        mutableKinds.value = getKindsUseCase.execute()
    }

    fun initStatuses() {
        mutableStatuses.value = getStatusesUseCase.execute()
    }

    fun initPriorities() {
        mutablePriorities.value = getPrioritiesUseCase.execute()
    }

    fun initFacilities() {
        viewModelScope.launch(getConnectionHandler()) {
            getFacilitiesUseCase.execute()?.let {
                mutableFacilities.value = it
            }
        }
    }

    fun initEmployees() {
        viewModelScope.launch(getConnectionHandler()) {
            getUsersUseCase.execute()?.let {
                mutableEmployees.value = it
            }
        }
    }

    private fun getConnectionHandler(): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, throwable ->
            if (throwable::class == ConnectException::class) {
                mutableErrors.value = listOf(TicketStates.NO_SERVER_CONNECTION)
            }
        }
    }

    fun checkTicketFields(ticket: AddTicketParams) {
        mutableFieldsCheckResult.value = checkTicketUseCase.execute(ticket)
    }
}
