package com.example.sidiay.presentation.viewmodels.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.entities.TicketEntity
import com.example.domain.usecases.menu.GetTicketsUseCase
import com.example.domain.utils.Debugger
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

    var searchList: ArrayList<TicketEntity> = arrayListOf()
    var filteredList: ArrayList<TicketEntity> = arrayListOf()
    var filteredSearchedList: ArrayList<TicketEntity> = arrayListOf()

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
                //TODO: add exceptions
            }
        }
    }
}