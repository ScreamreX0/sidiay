package com.example.sidiay.presentation.viewmodels.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.entities.Application
import com.example.domain.usecases.menu.GetApplicationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class ApplicationsFragmentViewModel @Inject constructor(
    private val getApplicationsUseCase: GetApplicationsUseCase
) : ViewModel() {
    var applications = MutableLiveData<List<Application>>()

    fun fillApplicationsList() {
        viewModelScope.launch(getApplicationsHandler()) {
            applications.value = getApplicationsUseCase.execute()
        }
    }

    private fun getApplicationsHandler(): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, throwable ->
            if (throwable::class == ConnectException::class) {
                //TODO: add exceptions
            }
        }
    }
}