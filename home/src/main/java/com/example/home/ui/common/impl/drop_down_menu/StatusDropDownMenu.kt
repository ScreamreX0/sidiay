package com.example.home.ui.common.impl.drop_down_menu

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.core.R
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.data_classes.params.TicketRestriction
import com.example.domain.enums.TicketFieldsEnum
import com.example.domain.enums.TicketStatuses
import com.example.home.ui.common.ICustomDropDownMenu

class StatusDropDownMenu(
    override val ticketFieldsParams: MutableState<TicketFieldParams> = mutableStateOf(TicketFieldParams()),
    override val field: TicketFieldsEnum = TicketFieldsEnum.STATUS,
    override val ticket: MutableState<TicketEntity>,
    override val ticketRestrictions: TicketRestriction,
    override val isValueNull: Boolean,
    private val selectedTicketStatus: MutableState<TicketStatuses>,
    val updateRestrictions: () -> Unit,
) : ICustomDropDownMenu {
    @Composable
    fun Content() {
        super.init(this, ticketRestrictions, isValueNull)
        if (!ticketFieldsParams.value.isVisible) return

        Component(
            items = ticketRestrictions.availableStatuses,
            onItemSelected = {
                selectedTicketStatus.value = it
                updateRestrictions()
            },
            selectedItem = selectedTicketStatus.value,
            title = "Статус",
            icon = R.drawable.baseline_help_outline_24,
            ticketFieldsParams = ticketFieldsParams.value,
            text = { it?.title ?: "[Статус не определен]" },
            label = { it.title }
        )
    }
}