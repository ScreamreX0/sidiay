package com.example.home.ui.common.impl.chip_rows

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.core.R
import com.example.core.utils.Helper
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.TicketData
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.data_classes.params.TicketRestriction
import com.example.domain.enums.TicketFieldsEnum
import com.example.home.ui.common.ICustomChipRow
import com.example.home.ui.common.components.CustomChip
import com.example.home.ui.common.components.ListElement

class FacilitiesChipRow(
    override val ticketFieldsParams: MutableState<TicketFieldParams> = mutableStateOf(TicketFieldParams()),
    override val field: TicketFieldsEnum = TicketFieldsEnum.FACILITIES,
    override val ticket: MutableState<TicketEntity>,
    override val ticketData: MutableState<TicketData?>,
    override val ticketRestrictions: TicketRestriction,
    override val isValueNull: Boolean
) : ICustomChipRow {
    @Composable
    fun Content() {
        super.init(this, ticketRestrictions, isValueNull)

        Component(
            dialogTitle = "Выберите объект",
            ticketData = ticketData.value?.facilities,
            predicate = { it, searchTextState ->
                it.name?.contains(searchTextState.text, true) ?: false
            },
            listItem = { it, isDialogOpened ->
                ListElement(title = it.name ?: "") {
                    if (ticket.value.facilities?.contains(it) != true) {
                        ticket.value = ticket.value.copy(
                            facilities = Helper.addToList(ticket.value.facilities, it)
                        )
                    }
                    isDialogOpened.value = false
                }
            },
            title = "Объекты",
            icon = R.drawable.baseline_oil_barrel_24,
            addingChipTitle = "Добавить объекты",
            chips = {
                ticket.value.facilities?.forEach {
                    CustomChip(title = it.name ?: "") {
                        ticket.value = ticket.value.copy(
                            facilities = Helper.removeFromList(ticket.value.facilities, it)
                        )
                    }
                }
            },
            ticketFieldsParams = ticketFieldsParams.value,
        )
    }
}