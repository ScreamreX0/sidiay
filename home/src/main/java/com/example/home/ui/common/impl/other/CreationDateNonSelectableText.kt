package com.example.home.ui.common.impl.other

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.core.R
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.data_classes.params.TicketRestriction
import com.example.domain.enums.TicketFieldsEnum
import com.example.home.ui.common.INonSelectableText

class CreationDateNonSelectableText(
    override val ticketFieldsParams: MutableState<TicketFieldParams> = mutableStateOf(TicketFieldParams()),
    override val field: TicketFieldsEnum = TicketFieldsEnum.AUTHOR,
    override val ticket: MutableState<TicketEntity>,
    override val ticketRestrictions: TicketRestriction,
    override val isValueNull: Boolean,
) : INonSelectableText {
    @Composable
    fun Content() {
        super.init(this, ticketRestrictions, isValueNull)

        Component(
            title = "Дата создания",
            icon = R.drawable.ic_baseline_person_24,
            label = ticket.value.creation_date ?: "[Дата создания не определена]",
            ticketFieldsParams = ticketFieldsParams.value,
        )
    }
}