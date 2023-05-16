package com.example.home.ui.common.impl.texts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.core.R
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.enums.ui.TicketFieldsEnum
import com.example.home.ui.common.interfaces.INonSelectableText

class DispatcherText(
    override val ticketFieldsParams: TicketFieldParams,
    private val ticket: MutableState<TicketEntity>,
    override val fieldEnum: TicketFieldsEnum = TicketFieldsEnum.DISPATCHER,
) : INonSelectableText<UserEntity> {
    @Composable
    fun Content() {
        if (!ticketFieldsParams.isVisible) return

        Component(
            title = "Диспетчер",
            icon = R.drawable.ic_baseline_person_24,
            label = ticket.value.dispatcher?.getFullName() ?: "[Диспетчер не определен]",
            ticketFieldsParams = ticketFieldsParams,
        )
    }
}