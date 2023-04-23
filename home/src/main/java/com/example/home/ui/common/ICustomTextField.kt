package com.example.home.ui.common

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.domain.data_classes.params.TicketFieldParams

internal interface ICustomTextField : ITicketField {
    @Composable
    fun Component(
        title: String,
        icon: Int,
        text: String?,
        onValueChange: (newValue: String) -> Unit,
        textFieldHint: String,
        ticketFieldsParams: TicketFieldParams
    ) {
        TicketFieldComponent(
            title = title,
            icon = icon,
            item = {
                CustomTextField(
                    text = text,
                    onValueChange = onValueChange,
                    hint = textFieldHint,
                    isClickable = ticketFieldsParams.isClickable
                )
            },
            ticketFieldsParams = ticketFieldsParams,
        )
    }

    @Composable
    private fun CustomTextField(
        text: String?,
        onValueChange: (newValue: String) -> Unit,
        hint: String,
        isClickable: Boolean
    ) {
        BasicTextField(
            enabled = isClickable,
            value = text ?: "",
            onValueChange = { onValueChange(it) },
            textStyle = TextStyle.Default.copy(
                fontSize = 24.sp,
                color = MaterialTheme.colors.onBackground,
            ),
            cursorBrush = SolidColor(MaterialTheme.colors.onBackground),
            decorationBox = { innerTextField ->
                if (text?.isEmpty() != false) {
                    Text(
                        fontSize = 24.sp,
                        color = MaterialTheme.colors.onBackground.copy(alpha = 0.7F),
                        text = text ?: hint,
                    )
                }
                innerTextField()
            }
        )
    }
}