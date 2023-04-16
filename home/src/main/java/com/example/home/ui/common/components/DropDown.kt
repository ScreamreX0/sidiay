package com.example.home.ui.common.components


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
import com.example.domain.enums.TicketStatuses

@Composable
internal fun DropDownComponent(
    modifier: Modifier = Modifier,
    items: List<TicketStatuses>,
    onItemSelected: (TicketStatuses) -> Unit,
    selectedItem: TicketStatuses?,
    isClickable: Boolean
) {
    val expanded = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .wrapContentSize()
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null
            ) { if (isClickable) expanded.value = true }
    ) {
        Text(
            modifier = modifier,
            text = selectedItem?.title ?: "[Статус не определен]",
            fontSize = MaterialTheme.typography.h5.fontSize,
            color = MaterialTheme.colors.onBackground,
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
                        text = item.title,
                        fontSize = MaterialTheme.typography.h5.fontSize,
                        color = MaterialTheme.colors.onBackground,
                    )
                }
            }
        }
    }
}