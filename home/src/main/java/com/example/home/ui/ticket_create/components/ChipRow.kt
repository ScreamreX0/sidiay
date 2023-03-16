package com.example.home.ui.ticket_create.components

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.core.ui.theme.AppTheme
import com.example.core.ui.utils.ScreenPreview
import com.example.domain.data_classes.entities.FacilityEntity
import com.example.domain.data_classes.entities.UserEntity
import com.google.accompanist.flowlayout.FlowRow

internal class ChipRow {
    @Composable
    fun Brigade(
        modifier: Modifier = Modifier,
        draftBrigade: MutableList<UserEntity?>,
        isDialogOpened: MutableState<Boolean> = remember { mutableStateOf(false) },
    ) {
        CustomChipRow(
            modifier = modifier,
            addingChipTitle = "Добавить сотрудников",
            isDialogOpened = isDialogOpened
        ) {
            draftBrigade.forEach {
                CustomChip(title = it?.getFullName() ?: "") { draftBrigade.remove(it) }
            }
        }
    }

    @Composable
    fun Facilities(
        modifier: Modifier = Modifier,
        draftFacilities: MutableList<FacilityEntity?>,
        isDialogOpened: MutableState<Boolean> = remember { mutableStateOf(false) },
    ) {
        CustomChipRow(
            modifier = modifier,
            addingChipTitle = "Добавить объекты",
            isDialogOpened = isDialogOpened
        ) {
            draftFacilities.forEach {
                CustomChip(title = it?.name ?: "") { draftFacilities.remove(it) }
            }
        }
    }

    @Composable
    private fun CustomChipRow(
        modifier: Modifier,
        addingChipTitle: String,
        isDialogOpened: MutableState<Boolean>,
        chips: @Composable () -> Unit,
    ) {
        FlowRow(
            modifier = modifier,
            mainAxisSpacing = 10.dp,
            crossAxisSpacing = 10.dp,
        ) {
            CustomChip(
                title = addingChipTitle,
                removeButtonVisible = false,
            ) { isDialogOpened.value = true }

            chips()
        }
    }

    @Composable
    private fun CustomChip(
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
                modifier = Modifier
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

    @Composable
    @ScreenPreview
    private fun Preview() {
        AppTheme {
            Facilities(
                Modifier,
                remember {
                    List(5) {
                        FacilityEntity(
                            id = it.toLong(),
                            name = "TestObject$it"
                        )
                    }.toMutableList()
                }
            )
        }
    }
}