package com.example.sidiay.presentation.viewmodels.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.enums.ticketstates.KindState
import com.example.domain.enums.ticketstates.PriorityState
import com.example.domain.enums.ticketstates.ServiceState
import com.example.domain.enums.other.AddTicketStates
import com.example.domain.enums.ticketstates.TicketStatuses
import com.example.domain.models.entities.FacilityEntity
import com.example.domain.models.entities.UserEntity
import com.example.domain.usecases.menu.create.*
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
    private val getObjectsUseCase: GetObjectsUseCase,
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {
    val serviceState: MutableLiveData<List<ServiceState>> = MutableLiveData()
    val kindState: MutableLiveData<List<KindState>> = MutableLiveData()
    val statuses: MutableLiveData<List<TicketStatuses>> = MutableLiveData()
    val priorityState: MutableLiveData<List<PriorityState>> = MutableLiveData()

    // Suspend vars
    val objects: MutableLiveData<List<FacilityEntity>> = MutableLiveData()
    val employees: MutableLiveData<List<UserEntity>> = MutableLiveData()

    var errorsList: MutableLiveData<List<AddTicketStates>> = MutableLiveData()

    fun initServices() {
        serviceState.value = getServicesUseCase.execute()
    }

    fun initKinds() {
        kindState.value = getKindsUseCase.execute()
    }

    fun initStatuses() {
        statuses.value = getStatusesUseCase.execute()
    }

    fun initPriorities() {
        priorityState.value = getPrioritiesUseCase.execute()
    }

    fun initObjects() {
        viewModelScope.launch(getConnectionHandler()) {
            objects.value = getObjectsUseCase.execute()
        }
    }

    fun initEmployees() {
        viewModelScope.launch(getConnectionHandler()) {
            employees.value = getUsersUseCase.execute()
        }
    }

    private fun getConnectionHandler(): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, throwable ->
            if (throwable::class == ConnectException::class) {
                errorsList.value = listOf(AddTicketStates.NO_SERVER_CONNECTION)
            }
        }
    }
}