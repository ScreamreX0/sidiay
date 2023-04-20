package com.example.domain.data_classes.params

import com.example.domain.enums.TicketFieldsEnum

data class TicketField(
    val field: TicketFieldsEnum,
    val isRequired: Boolean = false
)
