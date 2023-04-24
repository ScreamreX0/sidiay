package com.example.home.ui.common.impl.clickable_texts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.core.R
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.TicketData
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.data_classes.params.TicketRestriction
import com.example.domain.enums.TicketFieldsEnum
import com.example.home.ui.common.ICustomClickableText
import com.example.home.ui.common.components.ListElement

class ExecutorClickableText(
    override val field: UserEntity?,
    override val ticketData: List<UserEntity>?,
    override val ticketFieldsParams: TicketFieldParams,
    private val ticket: MutableState<TicketEntity>,
    override val fieldEnum: TicketFieldsEnum = TicketFieldsEnum.EXECUTOR,
) : ICustomClickableText<UserEntity, UserEntity> {
    @Composable
    fun Content() {
        if (!ticketFieldsParams.isVisible) return

        Component(
            dialogTitle = "Выберите исполнителя",
            ticketData = ticketData,
            predicate = { it, searchTextState ->
                it.employee?.name?.contains(searchTextState.text, true) ?: false
                        || it.employee?.firstname?.contains(searchTextState.text, true) ?: false
                        || it.employee?.lastname?.contains(searchTextState.text, true) ?: false
            },
            listItem = { it, isDialogOpened ->
                ListElement(title = it.getFullName() ?: "[ФИО]") {
                    ticket.value = ticket.value.copy(executor = it)
                    isDialogOpened.value = false
                }
            },
            title = "Исполнитель",
            label = ticket.value.executor?.getFullName() ?: "[Выбрать исполнителя]",
            icon = R.drawable.ic_baseline_person_24,
            ticketFieldsParams = ticketFieldsParams,
        )
    }
}