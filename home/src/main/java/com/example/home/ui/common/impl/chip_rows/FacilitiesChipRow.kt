package com.example.home.ui.common.impl.chip_rows

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.core.R
import com.example.core.utils.Helper
import com.example.domain.data_classes.entities.FacilityEntity
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.enums.ui.TicketFieldsEnum
import com.example.home.ui.common.interfaces.ICustomChipRow
import com.example.home.ui.common.components.CustomChip
import com.example.home.ui.common.components.ListElement

class FacilitiesChipRow(
    override val field: List<FacilityEntity>?,
    override val ticketData: List<FacilityEntity>?,
    override val ticketFieldsParams: TicketFieldParams,
    private val ticket: MutableState<TicketEntity>,
    override val fieldEnum: TicketFieldsEnum = TicketFieldsEnum.FACILITIES,
) : ICustomChipRow<FacilityEntity, List<FacilityEntity>> {
    @Composable
    fun Content() {
        if (!ticketFieldsParams.isVisible) return

        Component(
            dialogTitle = "Выберите объект",
            ticketData = ticketData,
            predicate = { it, searchTextState ->
                it.name?.contains(searchTextState.text, true) ?: false
            },
            listItem = { it, isDialogOpened ->
                ListElement(title = it.name ?: "") {
                    if (field?.contains(it) != true) {
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
                        if (ticketFieldsParams.isClickable) {
                            ticket.value = ticket.value.copy(
                                facilities = Helper.removeFromList(ticket.value.facilities, it)
                            )
                        }
                    }
                }
            },
            ticketFieldsParams = ticketFieldsParams,
        )
    }
}