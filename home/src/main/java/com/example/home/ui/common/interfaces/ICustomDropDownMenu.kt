package com.example.home.ui.common.interfaces

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.domain.data_classes.params.TicketFieldParams

internal interface ICustomDropDownMenu<T, E> : ITicketField<E> {
    val ticketData: List<T>?

    @Composable
    fun <T> Component(
        items: List<T>,
        onItemSelected: (T) -> Unit,
        selectedItem: T?,
        title: String,
        icon: Int,
        ticketFieldsParams: TicketFieldParams,
        text: (T?) -> String,
        label: (T) -> String
    ) {
        TicketFieldComponent(
            title = title,
            icon = icon,
            item = {
                val expanded = remember { mutableStateOf(false) }
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null
                        ) { if (ticketFieldsParams.isClickable) expanded.value = true }
                ) {
                    Text(
                        text = text(selectedItem),
                        fontSize = MaterialTheme.typography.h5.fontSize,
                        color = if (ticketFieldsParams.isClickable) {
                            MaterialTheme.colors.onBackground
                        } else {
                            MaterialTheme.colors.onBackground.copy(alpha = 0.6F)
                        },
                    )
                    DropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false }
                    ) {
                        items.forEach { item ->
                            DropdownMenuItem(
                                onClick = {
                                    onItemSelected(item)
                                    expanded.value = false
                                }) {
                                Text(
                                    text = label(item),
                                    fontSize = MaterialTheme.typography.h5.fontSize,
                                    color = MaterialTheme.colors.onBackground,
                                )
                            }
                        }
                    }
                }
            },
            ticketFieldsParams = ticketFieldsParams,
        )
    }
}