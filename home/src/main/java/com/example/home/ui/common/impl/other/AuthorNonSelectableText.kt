package com.example.home.ui.common.impl.other

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.core.R
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.AuthParams
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.data_classes.params.TicketRestriction
import com.example.domain.enums.TicketFieldsEnum
import com.example.home.ui.common.INonSelectableText

class AuthorNonSelectableText(
    override val field: UserEntity?,
    override val ticketFieldsParams: TicketFieldParams,
    private val ticket: MutableState<TicketEntity>,
    override val fieldEnum: TicketFieldsEnum = TicketFieldsEnum.AUTHOR,
) : INonSelectableText<UserEntity> {
    @Composable
    fun Content() {
        if (!ticketFieldsParams.isVisible) return

        Component(
            title = "Автор",
            icon = R.drawable.ic_baseline_person_24,
            label = ticket.value.author?.getFullName() ?: "[Автор не определен]",
            ticketFieldsParams = ticketFieldsParams,
        )
    }
}