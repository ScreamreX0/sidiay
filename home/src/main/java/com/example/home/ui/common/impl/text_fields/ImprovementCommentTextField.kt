package com.example.home.ui.common.impl.text_fields

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.core.R
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.enums.ui.TicketFieldsEnum
import com.example.home.ui.common.interfaces.ICustomTextField

class ImprovementCommentTextField(
    override val ticketFieldsParams: TicketFieldParams,
    private val ticket: MutableState<TicketEntity>,
    override val fieldEnum: TicketFieldsEnum = TicketFieldsEnum.IMPROVEMENT_COMMENT,
) : ICustomTextField<String> {
    @Composable
    fun Content() {
        if (!ticketFieldsParams.isVisible) return

        Component(
            title = "Причина доработки",
            icon = R.drawable.baseline_preview_24,
            text = ticket.value.improvement_comment,
            onValueChange = { ticket.value = ticket.value.copy(improvement_comment = it) },
            textFieldHint = ticket.value.improvement_comment ?: "Ввести причину доработки",
            ticketFieldsParams = ticketFieldsParams,
        )
    }
}