package com.example.sidiay.presentation.viewmodels.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.enums.ApplicationStatuses
import com.example.domain.enums.Kinds
import com.example.domain.enums.Priorities
import com.example.domain.enums.Services
import com.example.domain.usecases.menu.create.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class AddApplicationViewModel @Inject constructor(
    private val getServicesUseCase: GetServicesUseCase,
    private val getKindsUseCase: GetKindsUseCase,
    private val getStatusesUseCase: GetStatusesUseCase,
    private val getPrioritiesUseCase: GetPrioritiesUseCase,
    private val getObjectsUseCase: GetObjectsUseCase,
    private val getEmployeesUseCase: GetEmployeesUseCase
) : ViewModel() {

    val services: MutableLiveData<List<Services>> = MutableLiveData()
    val kinds: MutableLiveData<List<Kinds>> = MutableLiveData()
    val statuses: MutableLiveData<List<ApplicationStatuses>> = MutableLiveData()
    val priorities: MutableLiveData<List<Priorities>> = MutableLiveData()
    val employees: MutableLiveData<List<String>> = MutableLiveData()

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
        viewModelScope.launch(getEmployeesHandler()) {
            employees.value = getObjectsUseCase.execute()
        }
    }

    private fun getEmployeesHandler(): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, throwable ->
            if (throwable::class == ConnectException::class) {
                //TODO: add exceptions
            }
        }
    }
}