package com.example.home.ui.common.impl.texts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.core.R
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.enums.ui.TicketFieldsEnum
import com.example.home.ui.common.interfaces.INonSelectableText

class CreationDateText(
    override val ticketFieldsParams: TicketFieldParams,
    private val ticket: MutableState<TicketEntity>,
    override val fieldEnum: TicketFieldsEnum = TicketFieldsEnum.CREATION_DATE,
) : INonSelectableText<String> {
    @Composable
    fun Content() {
        if (!ticketFieldsParams.isVisible) return

        Component(
            title = "Дата создания",
            icon = R.drawable.ic_baseline_calendar_month_24,
            label = ticket.value.creation_date ?: "[Дата создания не определена]",
            ticketFieldsParams = ticketFieldsParams,
        )
    }
}