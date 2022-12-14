package com.example.sidiay.presentation.viewmodels.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.enums.ApplicationStatuses
import com.example.domain.enums.Kinds
import com.example.domain.enums.Priorities
import com.example.domain.enums.Services
import com.example.domain.usecases.menu.create.GetKindsUseCase
import com.example.domain.usecases.menu.create.GetPrioritiesUseCase
import com.example.domain.usecases.menu.create.GetServicesUseCase
import com.example.domain.usecases.menu.create.GetStatusesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddApplicationViewModel @Inject constructor(
    private val getServicesUseCase: GetServicesUseCase,
    private val getKindsUseCase: GetKindsUseCase,
    private val getStatusesUseCase: GetStatusesUseCase,
    private val getPrioritiesUseCase: GetPrioritiesUseCase
) : ViewModel() {

    val services: MutableLiveData<List<Services>> = MutableLiveData()
    val kinds: MutableLiveData<List<Kinds>> = MutableLiveData()
    val statuses: MutableLiveData<List<ApplicationStatuses>> = MutableLiveData()
    val priorities: MutableLiveData<List<Priorities>> = MutableLiveData()

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
}