package com.example.home.ui.common.impl.text_fields

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.core.R
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.enums.ui.TicketFieldsEnum
import com.example.home.ui.common.interfaces.ICustomTextField

class ExecutionProblemReasonTextField(
    override val ticketFieldsParams: TicketFieldParams,
    private val ticket: MutableState<TicketEntity>,
    override val fieldEnum: TicketFieldsEnum = TicketFieldsEnum.EXECUTION_PROBLEM_DESCRIPTION,
) : ICustomTextField<String> {
    @Composable
    fun Content() {
        if (!ticketFieldsParams.isVisible) return

        Component(
            title = "Описание проблемы",
            icon = R.drawable.baseline_description_24,
            text = ticket.value.execution_problem_description,
            onValueChange = { ticket.value = ticket.value.copy(execution_problem_description = it) },
            textFieldHint = ticket.value.execution_problem_description ?: "[Описание проблемы]",
            ticketFieldsParams = ticketFieldsParams,
        )
    }
}