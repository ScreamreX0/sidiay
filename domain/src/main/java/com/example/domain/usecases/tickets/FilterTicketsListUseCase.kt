package com.example.domain.usecases.tickets

import androidx.compose.runtime.mutableStateOf
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.FilteringParams
import com.example.domain.enums.ui.TicketFieldsEnum
import javax.inject.Inject

class FilterTicketsListUseCase @Inject constructor() {
    fun execute(
        filterParams: FilteringParams?,
        sortingParams: TicketFieldsEnum?,
        ticketsList: List<TicketEntity>?
    ): List<TicketEntity>? {
        ticketsList ?: return null

        val filteredList = mutableStateOf(ticketsList)

        filterParams?.let { params ->
            // FILTERING
            filteredList.value = filteredList.value.filterWithList(params.priority) { itTicket, it -> itTicket.priority?.id == it.id }
            filteredList.value = filteredList.value.filterWithList(params.services) { itTicket, it -> itTicket.service?.id == it.id }
            filteredList.value = filteredList.value.filterWithList(params.kinds) { itTicket, it -> itTicket.kind?.id == it.id }
            filteredList.value = filteredList.value.filterWithList(params.authors) { itTicket, it -> itTicket.author?.id == it.id }
            filteredList.value = filteredList.value.filterWithList(params.executors) { itTicket, it -> itTicket.executor?.id == it.id }
            filteredList.value = filteredList.value.filterWithList(params.status) { itTicket, it -> itTicket.status == it.value }
            filteredList.value = filteredList.value.filterWithList(params.brigade) { itTicket, it -> itTicket.brigade?.contains(it) ?: true }
            filteredList.value = filteredList.value.filterWithList(params.transport) { itTicket, it -> itTicket.transport?.contains(it) ?: true }
            filteredList.value = filteredList.value.filterWithList(params.facilities) { itTicket, it -> itTicket.facilities?.contains(it) ?: true }
            filteredList.value = filteredList.value.filterWithList(params.equipment) { itTicket, it -> itTicket.equipment?.contains(it) ?: true }
            filteredList.value = filteredList.value.filter { params.planeDate?.equals(it.plane_date) ?: true }
            filteredList.value = filteredList.value.filter { params.closingDate?.equals(it.closing_date) ?: true }
            filteredList.value = filteredList.value.filter { params.creationDate?.equals(it.creation_date) ?: true }
        }

        sortingParams?.let {
            // SORTING

            filteredList.value = filteredList.value.sortedBy {
                when (sortingParams) {
                    TicketFieldsEnum.ID -> { it.id.toString() }
                    TicketFieldsEnum.NAME -> { it.name ?: ""  }
                    TicketFieldsEnum.DESCRIPTION -> { it.description ?: ""  }
                    TicketFieldsEnum.SERVICE -> { it.service?.id?.toString() ?: ""  }
                    TicketFieldsEnum.KIND -> { it.kind?.id?.toString() ?: ""  }
                    TicketFieldsEnum.PRIORITY -> { it.priority?.id?.toString() ?: ""  }
                    TicketFieldsEnum.EXECUTOR -> { it.executor?.employee?.firstname ?: "" }
                    TicketFieldsEnum.COMPLETED_WORK -> { it.completed_work ?: "" }
                    TicketFieldsEnum.PLANE_DATE -> { it.plane_date ?: "" }
                    TicketFieldsEnum.CLOSING_DATE -> { it.closing_date ?: "" }
                    TicketFieldsEnum.CREATION_DATE -> { it.creation_date ?: "" }
                    TicketFieldsEnum.AUTHOR -> { it.author?.employee?.firstname ?: "" }
                    TicketFieldsEnum.STATUS -> { it.status.toString() }
                    TicketFieldsEnum.IMPROVEMENT_REASON -> { it.improvement_reason ?: "" }
                    else -> { it.id.toString() }
                }
            }
        }
        return filteredList.value
    }

    private fun <T> List<TicketEntity>.filterWithList(
        params: List<T>,
        predicate: (TicketEntity, T) -> Boolean
    ): List<TicketEntity> {
        if (params.isEmpty()) return this
        return params.flatMap { itParam -> this.filter { itTicket -> predicate(itTicket, itParam) } }
    }
}
