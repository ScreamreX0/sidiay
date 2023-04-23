package com.example.home.ui.common.impl.text_fields

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.core.R
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.data_classes.params.TicketRestriction
import com.example.domain.enums.TicketFieldsEnum
import com.example.home.ui.common.ICustomTextField

class CompletedWorkTextField(
    override val ticketFieldsParams: MutableState<TicketFieldParams> = mutableStateOf(TicketFieldParams()),
    override val field: TicketFieldsEnum = TicketFieldsEnum.COMPLETED_WORK,
    override val ticket: MutableState<TicketEntity>,
    override val ticketRestrictions: TicketRestriction,
    override val isValueNull: Boolean,
) : ICustomTextField {
    @Composable
    fun Content() {
        super.init(this, ticketRestrictions, isValueNull)

        Component(
            title = "Завершенная работа",
            icon = R.drawable.baseline_text_format_24,
            text = ticket.value.completed_work,
            onValueChange = { ticket.value = ticket.value.copy(completed_work = it) },
            textFieldHint = ticket.value.completed_work ?: "Ввести завершенную работу",
            ticketFieldsParams = ticketFieldsParams.value,
        )
    }
}