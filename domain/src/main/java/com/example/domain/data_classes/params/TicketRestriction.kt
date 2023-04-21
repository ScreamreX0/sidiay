package com.example.domain.data_classes.params

import com.example.domain.enums.TicketFieldsEnum
import com.example.domain.enums.TicketStatuses

data class TicketRestriction(
    val allowedFields: List<TicketFieldsEnum>,
    val requiredFields: List<TicketFieldsEnum>,
    val availableStatuses: List<TicketStatuses>
) {
    companion object {
        fun getEmpty() = TicketRestriction(listOf(), listOf(), listOf())
    }
}
