package com.example.home.ui.ticket_create

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.enums.TicketPriorityEnum
import com.example.domain.enums.TicketServiceEnum
import com.example.domain.enums.states.TicketCreateStates
import com.example.domain.enums.TicketKindEnum
import com.example.domain.enums.TicketStatusEnum
import com.example.domain.data_classes.entities.FacilityEntity
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.AddTicketParams
import com.example.domain.usecases.createticket.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class TicketCreateViewModel @Inject constructor(
//    private val getServicesUseCase: GetServicesUseCase,
//    private val getKindsUseCase: GetKindsUseCase,
//    private val getStatusesUseCase: GetStatusesUseCase,
//    private val getPrioritiesUseCase: GetPrioritiesUseCase,
//    private val getFacilitiesUseCase: GetFacilitiesUseCase,
//    private val getUsersUseCase: GetUsersUseCase,
//    private val saveTicketUseCase: SaveTicketUseCase,
//    private val checkTicketUseCase: CheckTicketUseCase
) : ViewModel() {
//    val mutableServices: MutableLiveData<List<TicketServiceEnum>> = MutableLiveData()
//    val mutableKinds: MutableLiveData<List<TicketKindEnum>> = MutableLiveData()
//    val mutableStatuses: MutableLiveData<List<TicketStatusEnum>> = MutableLiveData()
//    val mutablePriorities: MutableLiveData<List<TicketPriorityEnum>> = MutableLiveData()
//
//    // Suspend vars
//    val mutableFacilities: MutableLiveData<List<FacilityEntity>> = MutableLiveData()
//    val mutableEmployees: MutableLiveData<List<UserEntity>> = MutableLiveData()
//
//    var mutableErrors: MutableLiveData<List<TicketCreateStates>> = MutableLiveData()
//
//    var mutableSaveResult: MutableLiveData<Int> = MutableLiveData()
//
//    var mutableFieldsCheckResult: MutableLiveData<List<TicketCreateStates>> = MutableLiveData()
//
//    fun save(ticketEntity: AddTicketParams) {
//        viewModelScope.launch(getConnectionHandler()) {
//            mutableSaveResult.value = saveTicketUseCase.execute(ticketEntity)
//        }
//
//    }
//
//    fun initServices() {
//        mutableServices.value = getServicesUseCase.execute()
//    }
//
//    fun initKinds() {
//        mutableKinds.value = getKindsUseCase.execute()
//    }
//
//    fun initStatuses() {
//        mutableStatuses.value = getStatusesUseCase.execute()
//    }
//
//    fun initPriorities() {
//        mutablePriorities.value = getPrioritiesUseCase.execute()
//    }
//
//    fun initFacilities() {
//        viewModelScope.launch(getConnectionHandler()) {
//            getFacilitiesUseCase.execute()?.let {
//                mutableFacilities.value = it
//            }
//        }
//    }
//
//    fun initEmployees() {
//        viewModelScope.launch(getConnectionHandler()) {
//            getUsersUseCase.execute()?.let {
//                mutableEmployees.value = it
//            }
//        }
//    }
//
//    private fun getConnectionHandler(): CoroutineExceptionHandler {
//        return CoroutineExceptionHandler { _, throwable ->
//            if (throwable::class == ConnectException::class) {
//                mutableErrors.value = listOf(TicketCreateStates.NO_SERVER_CONNECTION)
//            }
//        }
//    }
//
//    fun checkTicketFields(ticket: AddTicketParams) {
//        mutableFieldsCheckResult.value = checkTicketUseCase.execute(ticket)
//    }
}
