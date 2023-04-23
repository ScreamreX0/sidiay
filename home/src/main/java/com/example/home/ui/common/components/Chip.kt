package com.example.home.ui.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow

@Composable
internal fun CustomChipRow(
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

@Composable
internal fun CustomChip(
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
                    painter = painterResource(id = com.example.core.R.drawable.ic_baseline_close_24),
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