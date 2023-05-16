package com.example.home.ui.common.impl.drop_down_menu

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.core.R
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.data_classes.params.TicketRestriction
import com.example.domain.enums.ui.TicketFieldsEnum
import com.example.domain.enums.TicketStatuses
import com.example.home.ui.common.interfaces.ICustomDropDownMenu

class StatusDropDownMenu(
    override val field: TicketStatuses?,
    override val ticketData: List<TicketStatuses>?,
    override val ticketFieldsParams: TicketFieldParams,
    private val updateRestrictions: () -> Unit,
    private val ticketRestrictions: TicketRestriction,
    private val selectedTicketStatus: MutableState<TicketStatuses>,
    override val fieldEnum: TicketFieldsEnum = TicketFieldsEnum.STATUS,
) : ICustomDropDownMenu<TicketStatuses, TicketStatuses> {
    @Composable
    fun Content() {
        if (!ticketFieldsParams.isVisible) return

        Component(
            items = ticketRestrictions.availableStatuses,
            onItemSelected = {
                selectedTicketStatus.value = it
                updateRestrictions()
            },
            selectedItem = selectedTicketStatus.value,
            title = "Статус",
            icon = R.drawable.baseline_priority_high_24,
            ticketFieldsParams = ticketFieldsParams,
            text = { it?.title ?: "[Статус не определен]" },
            label = { it.title }
        )
    }
}