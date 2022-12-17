package com.example.sidiay.presentation.viewmodels.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.enums.Kinds
import com.example.domain.enums.Priorities
import com.example.domain.enums.Services
import com.example.domain.enums.states.AddApplicationStates
import com.example.domain.enums.ApplicationStatuses
import com.example.domain.models.entities.Employee
import com.example.domain.models.entities.Object
import com.example.domain.models.entities.User
import com.example.domain.usecases.menu.create.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CreateApplicationViewModel @Inject constructor(
    private val getServicesUseCase: GetServicesUseCase,
    private val getKindsUseCase: GetKindsUseCase,
    private val getStatusesUseCase: GetStatusesUseCase,
    private val getPrioritiesUseCase: GetPrioritiesUseCase,
    private val getObjectsUseCase: GetObjectsUseCase,
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {
    val services: MutableLiveData<List<Services>> = MutableLiveData()
    val kinds: MutableLiveData<List<Kinds>> = MutableLiveData()
    val statuses: MutableLiveData<List<ApplicationStatuses>> = MutableLiveData()
    val priorities: MutableLiveData<List<Priorities>> = MutableLiveData()

    // Suspend vars
    val objects: MutableLiveData<List<Object>> = MutableLiveData()
    val employees: MutableLiveData<List<User>> = MutableLiveData()

    var errorsList: MutableLiveData<List<AddApplicationStates>> = MutableLiveData()

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

    fun create(
        objects: List<Objects>,
        service: Services,
        executor: Employee? = null,
        kind: Kinds,
        status: ApplicationStatuses = ApplicationStatuses.New,
        priority: Priorities,
        planeDate: Date? = null,
        expirationDate: Date? = null,
        description: String? = null,
        completedWork: String? = null,
        author: Employee,
        creationDate: Date
    ) {

    }

    private fun getConnectionHandler(): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, throwable ->
            if (throwable::class == ConnectException::class) {
                errorsList.value = listOf(AddApplicationStates.NO_SERVER_CONNECTION)
            }
        }
    }
}