package com.example.sidiay.presentation.viewmodels.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.enums.Kinds
import com.example.domain.enums.Priorities
import com.example.domain.enums.Services
import com.example.domain.enums.states.AddTicketStates
import com.example.domain.enums.TicketsStatuses
import com.example.domain.models.entities.Facility
import com.example.domain.models.entities.User
import com.example.domain.usecases.menu.create.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class CreateTicketViewModel @Inject constructor(
    private val getServicesUseCase: GetServicesUseCase,
    private val getKindsUseCase: GetKindsUseCase,
    private val getStatusesUseCase: GetStatusesUseCase,
    private val getPrioritiesUseCase: GetPrioritiesUseCase,
    private val getObjectsUseCase: GetObjectsUseCase,
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {
    val services: MutableLiveData<List<Services>> = MutableLiveData()
    val kinds: MutableLiveData<List<Kinds>> = MutableLiveData()
    val statuses: MutableLiveData<List<TicketsStatuses>> = MutableLiveData()
    val priorities: MutableLiveData<List<Priorities>> = MutableLiveData()

    // Suspend vars
    val objects: MutableLiveData<List<Facility>> = MutableLiveData()
    val employees: MutableLiveData<List<User>> = MutableLiveData()

    var errorsList: MutableLiveData<List<AddTicketStates>> = MutableLiveData()

    fun initServices() {
        services.value = getServicesUseCase.execute()
    }

    fun initKinds() {
        kinds.value = getKindsUseCase.execute()
    }

    fun initStatuses() {
        statuses.value = getStatusesUseCase.execute()
    }

    fun initPriorities() {
        priorities.value = getPrioritiesUseCase.execute()
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