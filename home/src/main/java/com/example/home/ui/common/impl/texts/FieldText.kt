package com.example.home.ui.common.impl.texts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.core.R
import com.example.domain.data_classes.entities.FieldsEntity
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.enums.ui.TicketFieldsEnum
import com.example.home.ui.common.interfaces.INonSelectableText

class FieldText(
    override val ticketFieldsParams: TicketFieldParams,
    private val ticket: MutableState<TicketEntity>,
    override val fieldEnum: TicketFieldsEnum = TicketFieldsEnum.FIELD,
) : INonSelectableText<FieldsEntity> {
    @Composable
    fun Content() {
        if (!ticketFieldsParams.isVisible) return

        Component(
            title = "Участок",
            icon = R.drawable.ic_baseline_person_24,
            label = ticket.value.field?.name ?: "[Участок не определен]",
            ticketFieldsParams = ticketFieldsParams,
        )
    }
}