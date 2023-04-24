package com.example.home.ui.common.impl.chip_rows

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.core.R
import com.example.core.utils.Helper
import com.example.domain.data_classes.entities.EquipmentEntity
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.TicketData
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.data_classes.params.TicketRestriction
import com.example.domain.enums.TicketFieldsEnum
import com.example.home.ui.common.ICustomChipRow
import com.example.home.ui.common.components.CustomChip
import com.example.home.ui.common.components.ListElement

class BrigadeChipRow(
    override val field: List<UserEntity>?,
    override val ticketData: List<UserEntity>?,
    override val ticketFieldsParams: TicketFieldParams,
    private val ticket: MutableState<TicketEntity>,
    override val fieldEnum: TicketFieldsEnum = TicketFieldsEnum.BRIGADE,
) : ICustomChipRow<UserEntity, List<UserEntity>> {
    @Composable
    fun Content() {
        if (!ticketFieldsParams.isVisible) return

        Component(
            dialogTitle = "Выберите сотрудника",
            ticketData = ticketData,
            predicate = { it, searchTextState ->
                it.employee?.name?.contains(searchTextState.text, true) ?: false
                        || it.employee?.firstname?.contains(searchTextState.text, true) ?: false
                        || it.employee?.lastname?.contains(searchTextState.text, true) ?: false
            },
            listItem = { it, isDialogOpened ->
                ListElement(title = it.getFullName() ?: "[ФИО]") {
                    if (ticket.value.brigade?.contains(it) != true) {
                        ticket.value = ticket.value.copy(
                            brigade = Helper.addToList(ticket.value.brigade, it)
                        )
                    }
                    isDialogOpened.value = false
                }
            },
            title = "Бригада",
            icon = R.drawable.baseline_people_24,
            addingChipTitle = "Добавить сотрудников",
            chips = {
                ticket.value.brigade?.forEach {
                    CustomChip(title = it.getFullName() ?: "") {
                        ticket.value = ticket.value.copy(
                            brigade = Helper.removeFromList(ticket.value.brigade, it)
                        )
                    }
                }
            },
            ticketFieldsParams = ticketFieldsParams,
        )
    }
}