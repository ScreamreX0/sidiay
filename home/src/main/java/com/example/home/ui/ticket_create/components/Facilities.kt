package com.example.home.ui.ticket_create.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.core.ui.theme.AppTheme
import com.example.core.ui.utils.ScreenPreview
import com.example.domain.data_classes.entities.DraftEntity
import com.example.domain.data_classes.entities.FacilityEntity
import com.example.domain.data_classes.params.TicketCreateParams
import com.google.accompanist.flowlayout.FlowRow

internal class Facilities {
    @Composable
    fun Content(
        modifier: Modifier,
        draft: MutableState<DraftEntity>,
        isDialogOpened: MutableState<Boolean> = remember { mutableStateOf(false) },
    ) {
        FlowRow(
            modifier = modifier,
            mainAxisSpacing = 16.dp,
            crossAxisSpacing = 16.dp,
        ) {
            FacilityChip(
                modifier = Modifier.clickable { isDialogOpened.value = true },
                title = "Добавить объекты",
                removeButtonVisible = false,
            ) {}
            draft.value.facilities?.forEach { itFacility ->
                FacilityChip(title = itFacility.name) {
                    draft.value.facilities = draft.value.facilities!!.filter {
                        it != itFacility
                    }.toList()
                }
            }
        }
    }

    //
    // Dialog
    //
    @Composable
    fun FacilitiesDialog(
        facilitiesScrollState: ScrollState,
        isDialogOpened: MutableState<Boolean>,
        fields: MutableState<TicketCreateParams?>,
        draft: MutableState<DraftEntity>,
        selectedFacilities: MutableList<Boolean>,
    ) {
        Dialog(onDismissRequest = { isDialogOpened.value = false }) {
            Column(
                Modifier
                    .background(MaterialTheme.colors.background)
                    .verticalScroll(facilitiesScrollState)
            ) {
                // List
                fields.value?.facilities?.forEachIndexed { index, it ->
                    DialogItem(
                        title = it.name,
                        checked = selectedFacilities[index]
                    ) { selectedFacilities[index] = !selectedFacilities[index] }
                }
                // OK
                Button(onClick = {
                    draft.value.facilities = fields.value?.facilities?.filterIndexed { index, _ ->
                        selectedFacilities[index]
                    }?.toList() ?: listOf()
                }) {
                    Text(text = "OK")
                }
                // Cancel
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Отмена")
                }
            }
        }
    }

    @Composable
    private fun DialogItem(
        title: String,
        checked: Boolean,
        check: () -> Unit
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { check() },
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colors.onBackground,
                uncheckedColor = MaterialTheme.colors.onBackground,
                checkmarkColor = MaterialTheme.colors.background,
            )
        )
        Text(text = title)
    }

    //
    // Chips
    //
    @Composable
    private fun FacilityChip(
        modifier: Modifier = Modifier,
        title: String,
        removeButtonVisible: Boolean = true,
        remove: () -> Unit,
    ) {
        Box(
            modifier = modifier
                .padding(end = 10.dp)
                .height(35.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colors.primary)
        ) {
            Row(
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (removeButtonVisible) {
                    Icon(
                        modifier = Modifier.clickable { remove() },
                        painter = painterResource(id = com.example.core.R.drawable.ic_baseline_close_24),
                        contentDescription = "Remove item",
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
                Text(
                    text = title,
                    color = MaterialTheme.colors.onPrimary,
                    fontSize = MaterialTheme.typography.h3.fontSize,
                )
            }
        }
    }

    @Composable
    @ScreenPreview
    private fun Preview() {
        AppTheme {
            Content(
                Modifier,
                remember {
                    mutableStateOf(DraftEntity(
                        facilities = List(5) {
                            FacilityEntity(
                                id = it.toLong(),
                                name = "TestObject$it"
                            )
                        }
                    ))
                },
            )
        }
    }
}