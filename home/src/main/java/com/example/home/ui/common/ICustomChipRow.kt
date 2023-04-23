package com.example.home.ui.common

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.core.R
import com.example.domain.data_classes.params.TicketFieldParams
import com.google.accompanist.flowlayout.FlowRow

internal interface ICustomChipRow : ITicketField {
    @Composable
    fun <T> Component(
        // Dialog
        dialogTitle: String,
        ticketData: List<T>?,
        predicate: (T, TextFieldValue) -> Boolean,
        listItem: @Composable (T, isDialogOpened: MutableState<Boolean>) -> Unit,
        searchTextState: MutableState<TextFieldValue> = remember { mutableStateOf(TextFieldValue("")) },
        isDialogOpened: MutableState<Boolean> = remember { mutableStateOf(false) },
        scrollState: ScrollState = rememberScrollState(),

        // Chip row
        title: String,
        icon: Int,
        addingChipTitle: String,
        chips: @Composable () -> Unit,
        ticketFieldsParams: TicketFieldParams
    ) {
        CustomDialog(
            isDialogOpened = isDialogOpened,
            topAppBarTitle = dialogTitle,
            scrollState = scrollState,
            fields = ticketData,
            predicate = predicate,
            listItem = { listItem(it, isDialogOpened) },
            searchTextState = searchTextState
        )
        TicketFieldComponent(
            title = title,
            icon = icon,
            item = {
                CustomChipRow(
                    addingChipTitle = addingChipTitle,
                    isDialogOpened = isDialogOpened,
                    chips = { chips() },
                    isClickable = ticketFieldsParams.isClickable
                )
            },
            ticketFieldsParams = ticketFieldsParams,
        )
    }

    @Composable
    fun CustomChip(
        modifier: Modifier = Modifier,
        title: String,
        removeButtonVisible: Boolean = true,
        onClick: () -> Unit,
    ) {
        Box(
            modifier = modifier
                .height(35.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colors.onBackground)
        ) {
            Row(
                modifier = modifier
                    .padding(start = 10.dp, end = 10.dp, top = 5.dp)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) { onClick() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (removeButtonVisible) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_close_24),
                        contentDescription = "Remove item",
                        tint = MaterialTheme.colors.onPrimary,
                    )
                }

                Text(
                    text = title,
                    fontSize = MaterialTheme.typography.h3.fontSize,
                    color = MaterialTheme.colors.onPrimary,
                )
            }
        }
    }

    @Composable
    private fun CustomChipRow(
        modifier: Modifier = Modifier,
        addingChipTitle: String,
        isDialogOpened: MutableState<Boolean>,
        chips: @Composable () -> Unit,
        isClickable: Boolean = true
    ) {
        FlowRow(
            modifier = modifier,
            mainAxisSpacing = 10.dp,
            crossAxisSpacing = 10.dp,
        ) {
            CustomChip(
                modifier = modifier,
                title = addingChipTitle,
                removeButtonVisible = false,
            ) { if (isClickable) isDialogOpened.value = true }

            chips()
        }
    }
}