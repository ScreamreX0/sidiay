package com.example.home.ui.ticket_create.components

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.core.ui.theme.AppTheme
import com.example.core.ui.utils.ComponentPreview
import com.example.domain.data_classes.entities.FacilityEntity
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.TicketCreateParams

class SingleSelectionDialog {
    @Composable
    fun BrigadeDialog(
        brigadeScrollState: ScrollState,
        isDialogOpened: MutableState<Boolean>,
        fields: MutableState<TicketCreateParams?>,
        draftBrigade: MutableList<UserEntity?>,
    ) {
        val searchTextState: MutableState<TextFieldValue> = remember {
            mutableStateOf(TextFieldValue(""))
        }
        CustomDialog(
            isDialogOpened = isDialogOpened,
            topAppBarTitle = "Выберите сотрудника",
            searchTextState = searchTextState,
        ) {
            ScrollableList(scrollState = brigadeScrollState) {
                // Searching
                if (searchTextState.value.text.isNotBlank()) {
                    fields.value?.users?.filter {
                        it.name?.contains(searchTextState.value.text) ?: false
                                || it.firstname?.contains(searchTextState.value.text) ?: false
                                || it.lastname?.contains(searchTextState.value.text) ?: false
                    }?.forEach {
                        ListElement(title = it.getFullName() ?: "[ФИО]") {
                            if (!draftBrigade.contains(it)) {
                                draftBrigade.add(it)
                            }
                            isDialogOpened.value = false
                        }
                    }
                } else {
                    fields.value?.users?.forEach {
                        ListElement(title = it.getFullName() ?: "[ФИО]") {
                            if (!draftBrigade.contains(it)) {
                                draftBrigade.add(it)
                            }
                            isDialogOpened.value = false
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun FacilitiesDialog(
        facilitiesScrollState: ScrollState,
        isDialogOpened: MutableState<Boolean>,
        fields: MutableState<TicketCreateParams?>,
        draftFacilities: MutableList<FacilityEntity?>,
    ) {
        val searchTextState: MutableState<TextFieldValue> = remember {
            mutableStateOf(TextFieldValue(""))
        }
        CustomDialog(
            isDialogOpened = isDialogOpened,
            topAppBarTitle = "Выберите объект",
            searchTextState = searchTextState,
        ) {
            ScrollableList(scrollState = facilitiesScrollState) {
                // Searching
                if (searchTextState.value.text.isNotBlank()) {
                    fields.value?.facilities?.filter {
                        it.name.contains(searchTextState.value.text)
                    }?.forEach {
                        ListElement(title = it.name) {
                            if (!draftFacilities.contains(it)) {
                                draftFacilities.add(it)
                            }
                            isDialogOpened.value = false
                        }
                    }
                } else {
                    fields.value?.facilities?.forEach {
                        ListElement(title = it.name) {
                            if (!draftFacilities.contains(it)) {
                                draftFacilities.add(it)
                            }
                            isDialogOpened.value = false
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun CustomDialog(
        isDialogOpened: MutableState<Boolean>,
        topAppBarTitle: String,
        searchTextState: MutableState<TextFieldValue>,
        ListComponent: @Composable () -> Unit,
    ) {
        val isSearchSelected = remember { mutableStateOf(false) }

        Dialog(onDismissRequest = { isDialogOpened.value = false }) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .padding(start = 15.dp, top = 15.dp, end = 15.dp)
            ) {
                // Top bar
                CustomTopAppBar(
                    isSearchSelected = isSearchSelected,
                    searchTextState = searchTextState,
                    title = topAppBarTitle,
                )
                CustomDivider()

                // List
                ListComponent()

                // Bottom buttons
                CustomDivider()
                CancelButton { isDialogOpened.value = false }
            }
        }
    }

    @Composable
    private fun CustomTopAppBar(
        isSearchSelected: MutableState<Boolean>,
        searchTextState: MutableState<TextFieldValue>,
        title: String,
    ) {
        Row(
            modifier = Modifier
                .height(60.dp)
                .width(250.dp)
                .padding(bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            if (isSearchSelected.value) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = searchTextState.value,
                    onValueChange = { searchTextState.value = it },
                    leadingIcon = {
                        Icon(
                            Icons.Default.ArrowBack,
                            modifier = Modifier
                                .clickable { isSearchSelected.value = false },
                            contentDescription = null,
                            tint = MaterialTheme.colors.onBackground
                        )
                    },
                    trailingIcon = {
                        Icon(
                            modifier = Modifier
                                .clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null
                                ) { searchTextState.value = TextFieldValue("") },
                            imageVector = Icons.Default.Close,
                            tint = MaterialTheme.colors.onBackground,
                            contentDescription = null,
                        )
                    },
                    singleLine = true,
                    textStyle = TextStyle.Default.copy(fontSize = 12.sp),
                    shape = RectangleShape,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = MaterialTheme.colors.onBackground,
                        cursorColor = MaterialTheme.colors.onBackground,
                        leadingIconColor = MaterialTheme.colors.onBackground,
                        trailingIconColor = MaterialTheme.colors.onBackground,
                        backgroundColor = MaterialTheme.colors.background,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                )
            } else {
                Text(
                    color = MaterialTheme.colors.onBackground,
                    text = title,
                    fontSize = MaterialTheme.typography.h3.fontSize,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    modifier = Modifier
                        .height(20.dp)
                        .width(20.dp)
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null
                        ) {
                            isSearchSelected.value = true
                        },
                    painter = painterResource(id = com.example.core.R.drawable.ic_baseline_search_24_white),
                    contentDescription = "Search",
                    tint = MaterialTheme.colors.onBackground
                )
            }
        }
    }

    @Composable
    private fun ScrollableList(
        scrollState: ScrollState,
        content: @Composable () -> Unit
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .heightIn(min = 50.dp, max = 300.dp)
                .width(250.dp)
                .padding(bottom = 10.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()
        }
    }

    @Composable
    private fun ListElement(title: String, onClick: () -> Unit) {
        Text(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .clickable {
                    onClick()
                },
            text = title,
            color = MaterialTheme.colors.onBackground,
            fontSize = MaterialTheme.typography.h3.fontSize,
        )
    }

    @Composable
    private fun CustomDivider() {
        Row(
            modifier = Modifier
                .height(1.dp)
                .width(250.dp)
                .background(MaterialTheme.colors.onBackground)
        ) {}
    }

    @Composable
    private fun CancelButton(onClick: () -> Unit) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) { onClick() }
                .height(50.dp)
                .width(250.dp)
                .padding(end = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                color = MaterialTheme.colors.onBackground,
                text = "Отмена",
                fontSize = MaterialTheme.typography.h4.fontSize,
            )
        }
    }

    @Composable
    @ComponentPreview
    private fun Preview() {
        val params = TicketCreateParams(
            facilities = List(30) {
                FacilityEntity(
                    it.toLong(),
                    it.toString()
                )
            }
        )
        AppTheme(isSystemInDarkTheme()) {
            FacilitiesDialog(
                rememberScrollState(),
                remember { mutableStateOf(true) },
                remember { mutableStateOf(params) },
                remember { mutableListOf() }
            )
        }
    }
}