package com.example.home.ui.common.interfaces

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.home.ui.common.components.CustomTextField

internal interface ICustomTextField<E> : ITicketField<E> {
    @Composable
    fun Component(
        title: String,
        icon: Int,
        text: String?,
        onValueChange: (newValue: String) -> Unit,
        textFieldHint: String,
        ticketFieldsParams: TicketFieldParams,
        keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    ) {
        TicketFieldComponent(
            title = title,
            icon = icon,
            item = {
                CustomTextField(
                    text = text,
                    onValueChange = onValueChange,
                    hint = textFieldHint,
                    isClickable = ticketFieldsParams.isClickable,
                    keyboardOptions = keyboardOptions
                )
            },
            ticketFieldsParams = ticketFieldsParams,
        )
    }
}