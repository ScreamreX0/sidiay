package com.example.domain.data_classes.params

import com.example.domain.enums.ui.TicketFieldsEnum

data class SortingParams(
    val field: TicketFieldsEnum? = null,
    val sortBy: SortBy = SortBy.DESCENDING
)

enum class SortBy {
    ASCENDING,
    DESCENDING
}