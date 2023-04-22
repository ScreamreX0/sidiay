package com.example.home.ui.common.impl.date_pickers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.core.R
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.data_classes.params.TicketRestriction
import com.example.domain.enums.TicketFieldsEnum
import com.example.home.ui.common.ICustomDatePicker
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PlaneDatePicker(
    override val field: TicketFieldsEnum = TicketFieldsEnum.PLANE_DATE,
    override val ticketFieldsParams: MutableState<TicketFieldParams> = mutableStateOf(TicketFieldParams.getEmpty()),
) : ICustomDatePicker {
    @Composable
    fun Content(
        ticket: MutableState<TicketEntity>,
        ticketRestrictions: TicketRestriction
    ) {
        super.Content(ticketRestrictions)

        Component(
            date = ticket.value.plane_date ?: LocalDate.now().toString(),
            onConfirmDatePicker = { ticket.value = ticket.value.copy(plane_date = it) },
            title = "Плановая дата",
            icon = R.drawable.ic_baseline_calendar_month_24,
            datePickerTitle = ticket.value.plane_date?.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                ?: "[Выбрать плановую дату]",
            ticketFieldsParams = ticketFieldsParams.value,
        )
    }
}