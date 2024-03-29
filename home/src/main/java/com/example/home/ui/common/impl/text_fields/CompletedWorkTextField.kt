package com.example.home.ui.common.impl.text_fields

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.core.R
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.enums.ui.TicketFieldsEnum
import com.example.home.ui.common.interfaces.ICustomTextField

class CompletedWorkTextField(
    override val ticketFieldsParams: TicketFieldParams,
    private val ticket: MutableState<TicketEntity>,
    override val fieldEnum: TicketFieldsEnum = TicketFieldsEnum.COMPLETED_WORK,
) : ICustomTextField<String> {
    @Composable
    fun Content() {
        if (!ticketFieldsParams.isVisible) return

        Component(
            title = "Завершенная работа",
            icon = R.drawable.baseline_fast_forward_24,
            text = ticket.value.completed_work,
            onValueChange = { ticket.value = ticket.value.copy(completed_work = it) },
            textFieldHint = ticket.value.completed_work ?: "Ввести завершенную работу",
            ticketFieldsParams = ticketFieldsParams,
        )
    }
}