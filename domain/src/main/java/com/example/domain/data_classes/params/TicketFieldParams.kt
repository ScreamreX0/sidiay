package com.example.domain.data_classes.params

data class TicketFieldParams(
    val starred: Boolean,
    val isClickable: Boolean
) {
    companion object {
        fun getEmpty() = TicketFieldParams(starred = false, isClickable = false)
    }
}