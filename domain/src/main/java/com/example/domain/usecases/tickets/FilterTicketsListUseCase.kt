package com.example.domain.usecases.tickets

import androidx.compose.runtime.mutableStateOf
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.FilteringParams
import com.example.domain.enums.KindsEnum
import com.example.domain.enums.PrioritiesEnum
import com.example.domain.enums.ServicesEnum
import com.example.domain.enums.ui.TicketFieldsEnum
import javax.inject.Inject

class FilterTicketsListUseCase @Inject constructor() {
    suspend fun execute(
        filterParams: FilteringParams?,
        sortingParams: TicketFieldsEnum?,
        ticketsList: List<TicketEntity>?
    ): List<TicketEntity>? {
        ticketsList ?: return null

        val filteredList = mutableStateOf(ticketsList)

        filterParams?.let { params ->
            // FILTERING
            filteredList.value = filteredList.value.filterWithList(params.priority) { itTicket, it -> itTicket.priority == it.value }
            filteredList.value = filteredList.value.filterWithList(params.services) { itTicket, it -> itTicket.service == it.value }
            filteredList.value = filteredList.value.filterWithList(params.kinds) { itTicket, it -> itTicket.kind == it.value }
            filteredList.value = filteredList.value.filterWithList(params.status) { itTicket, it -> itTicket.status == it.value }
            filteredList.value = filteredList.value.filter { params.planeDate?.equals(it.plane_date) ?: true }
            filteredList.value = filteredList.value.filter { params.closingDate?.equals(it.closing_date) ?: true }
            filteredList.value = filteredList.value.filter { params.creationDate?.equals(it.creation_date) ?: true }
        }

        sortingParams?.let {
            // SORTING

            filteredList.value = filteredList.value.sortedBy {
                when (sortingParams) {
                    TicketFieldsEnum.ID -> { it.id.toString() }
                    TicketFieldsEnum.TICKET_NAME -> { it.ticket_name ?: ""  }
                    TicketFieldsEnum.DESCRIPTION_OF_WORK -> { it.description_of_work ?: ""  }
                    TicketFieldsEnum.SERVICE -> { it.service?.let { itService -> ServicesEnum.getByValue(itService) }?.label ?: ""  }
                    TicketFieldsEnum.KIND -> { it.kind?.let { itKind -> KindsEnum.getByValue(itKind) }?.label ?: ""  }
                    TicketFieldsEnum.PRIORITY -> { it.priority?.let { itPriority -> PrioritiesEnum.getByValue(itPriority) }?.label ?: ""  }
                    TicketFieldsEnum.COMPLETED_WORK -> { it.completed_work ?: "" }
                    TicketFieldsEnum.PLANE_DATE -> { it.plane_date ?: "" }
                    TicketFieldsEnum.CLOSING_DATE -> { it.closing_date ?: "" }
                    TicketFieldsEnum.CREATION_DATE -> { it.creation_date ?: "" }
                    TicketFieldsEnum.AUTHOR -> { it.author?.employee?.firstname ?: "" }
                    TicketFieldsEnum.STATUS -> { it.status.toString() }
                    TicketFieldsEnum.IMPROVEMENT_COMMENT -> { it.improvement_comment ?: "" }
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
