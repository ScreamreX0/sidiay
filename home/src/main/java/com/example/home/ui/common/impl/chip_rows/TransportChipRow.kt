package com.example.home.ui.common.impl.chip_rows

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.core.R
import com.example.core.utils.Helper
import com.example.domain.data_classes.entities.FacilityEntity
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.entities.TransportEntity
import com.example.domain.data_classes.params.TicketData
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.data_classes.params.TicketRestriction
import com.example.domain.enums.TicketFieldsEnum
import com.example.home.ui.common.ICustomChipRow
import com.example.home.ui.common.components.CustomChip
import com.example.home.ui.common.components.ListElement

class TransportChipRow(
    override val field: List<TransportEntity>?,
    override val ticketData: List<TransportEntity>?,
    override val ticketFieldsParams: TicketFieldParams,
    private val ticket: MutableState<TicketEntity>,
    override val fieldEnum: TicketFieldsEnum = TicketFieldsEnum.TRANSPORT,
) : ICustomChipRow<TransportEntity, List<TransportEntity>> {
    @Composable
    fun Content() {
        if (!ticketFieldsParams.isVisible) return

        Component(
            dialogTitle = "Выберите транспорт",
            ticketData = ticketData,
            predicate = { it, searchTextState ->
                it.name?.contains(searchTextState.text, true) ?: false
            },
            listItem = { it, isDialogOpened ->
                ListElement(title = it.name ?: "") {
                    if (ticket.value.transport?.contains(it) != true) {
                        ticket.value = ticket.value.copy(
                            transport = Helper.addToList(ticket.value.transport, it)
                        )
                    }
                    isDialogOpened.value = false
                }
            },
            title = "Транспорт",
            icon = R.drawable.baseline_oil_barrel_24,
            addingChipTitle = "Добавить транспорт",
            chips = {
                ticket.value.transport?.forEach {
                    CustomChip(title = it.name ?: "") {
                        ticket.value = ticket.value.copy(
                            transport = Helper.removeFromList(ticket.value.transport, it)
                        )
                    }
                }
            },
            ticketFieldsParams = ticketFieldsParams,
        )
    }
}