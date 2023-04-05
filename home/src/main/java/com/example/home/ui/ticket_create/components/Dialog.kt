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
import com.example.core.utils.ComponentPreview
import com.example.domain.data_classes.entities.*
import com.example.domain.data_classes.params.TicketData

@Composable
internal fun ExecutorDialog(
    scrollState: ScrollState,
    isDialogOpened: MutableState<Boolean>,
    fields: MutableState<TicketData?>,
    draftExecutor: MutableState<UserEntity?>,
    searchTextState: MutableState<TextFieldValue> = remember { mutableStateOf(TextFieldValue("")) },
) {
    CustomDialog(
        isDialogOpened = isDialogOpened,
        topAppBarTitle = "Выберите сотрудника",
        scrollState = scrollState,
        fields = fields.value?.users,
        predicate = {
            it.employee?.name?.contains(searchTextState.value.text, true) ?: false
                    || it.employee?.firstname?.contains(searchTextState.value.text, true) ?: false
                    || it.employee?.lastname?.contains(searchTextState.value.text, true) ?: false
        },
        listItem = {
            ListElement(title = it.getFullName() ?: "[ФИО]") {
                draftExecutor.value = it
                isDialogOpened.value = false
            }
        },
        searchTextState = searchTextState,
    )
}

@Composable
internal fun PriorityDialog(
    scrollState: ScrollState,
    isDialogOpened: MutableState<Boolean>,
    fields: MutableState<TicketData?>,
    draftPriority: MutableState<PriorityEntity?>,
    searchTextState: MutableState<TextFieldValue> = remember { mutableStateOf(TextFieldValue("")) },
) {
    CustomDialog(
        isDialogOpened = isDialogOpened,
        topAppBarTitle = "Выберите приоритет",
        scrollState = scrollState,
        fields = fields.value?.priorities,
        predicate = { it.name?.contains(searchTextState.value.text, true) ?: false },
        listItem = {
            ListElement(title = it.name ?: "") {
                draftPriority.value = it
                isDialogOpened.value = false
            }
        },
        searchTextState = searchTextState,
    )
}

@Composable
internal fun KindDialog(
    scrollState: ScrollState,
    isDialogOpened: MutableState<Boolean>,
    fields: MutableState<TicketData?>,
    draftKinds: MutableState<KindEntity?>,
    searchTextState: MutableState<TextFieldValue> = remember { mutableStateOf(TextFieldValue("")) },
) {
    CustomDialog(
        isDialogOpened = isDialogOpened,
        topAppBarTitle = "Выберите вид",
        scrollState = scrollState,
        fields = fields.value?.kinds,
        predicate = { it.name?.contains(searchTextState.value.text, true) ?: false },
        listItem = {
            ListElement(title = it.name ?: "") {
                draftKinds.value = it
                isDialogOpened.value = false
            }
        },
        searchTextState = searchTextState,
    )
}

@Composable
internal fun ServiceDialog(
    scrollState: ScrollState,
    isDialogOpened: MutableState<Boolean>,
    fields: MutableState<TicketData?>,
    draftService: MutableState<ServiceEntity?>,
    searchTextState: MutableState<TextFieldValue> = remember { mutableStateOf(TextFieldValue("")) },
) {
    CustomDialog(
        isDialogOpened = isDialogOpened,
        topAppBarTitle = "Выберите сервис",
        scrollState = scrollState,
        fields = fields.value?.services,
        predicate = { it.name?.contains(searchTextState.value.text, true) ?: false },
        listItem = {
            ListElement(title = it.name ?: "") {
                draftService.value = it
                isDialogOpened.value = false
            }
        },
        searchTextState = searchTextState,
    )
}

@Composable
internal fun BrigadeDialog(
    scrollState: ScrollState,
    isDialogOpened: MutableState<Boolean>,
    fields: MutableState<TicketData?>,
    draftBrigade: MutableList<UserEntity?>,
    searchTextState: MutableState<TextFieldValue> = remember { mutableStateOf(TextFieldValue("")) },
) {
    CustomDialog(
        isDialogOpened = isDialogOpened,
        topAppBarTitle = "Выберите сотрудника",
        scrollState = scrollState,
        fields = fields.value?.users,
        predicate = {
            it.employee?.name?.contains(searchTextState.value.text, true) ?: false
                    || it.employee?.firstname?.contains(searchTextState.value.text, true) ?: false
                    || it.employee?.lastname?.contains(searchTextState.value.text, true) ?: false
        },
        listItem = {
            ListElement(title = it.getFullName() ?: "[ФИО]") {
                if (!draftBrigade.contains(it)) {
                    draftBrigade.add(it)
                }
                isDialogOpened.value = false
            }
        },
        searchTextState = searchTextState,
    )
}

@Composable
internal fun FacilitiesDialog(
    scrollState: ScrollState,
    isDialogOpened: MutableState<Boolean>,
    fields: MutableState<TicketData?>,
    draftFacilities: MutableList<FacilityEntity?>,
    searchTextState: MutableState<TextFieldValue> = remember { mutableStateOf(TextFieldValue("")) },
) {
    CustomDialog(
        isDialogOpened = isDialogOpened,
        topAppBarTitle = "Выберите объект",
        scrollState = scrollState,
        fields = fields.value?.facilities,
        predicate = { it.name?.contains(searchTextState.value.text, true) ?: false },
        listItem = {
            ListElement(title = it.name ?: "") {
                if (!draftFacilities.contains(it)) {
                    draftFacilities.add(it)
                }
                isDialogOpened.value = false
            }
        },
        searchTextState = searchTextState,
    )
}

@Composable
private fun <T> CustomDialog(
    isDialogOpened: MutableState<Boolean>,
    topAppBarTitle: String,
    isSearchSelected: MutableState<Boolean> = remember { mutableStateOf(false) },
    scrollState: ScrollState,
    fields: List<T>?,
    predicate: (T) -> Boolean,
    listItem: @Composable (T) -> Unit,
    searchTextState: MutableState<TextFieldValue> = remember { mutableStateOf(TextFieldValue("")) },
) {
    if (!isDialogOpened.value) {
        return
    }

    Dialog(onDismissRequest = { isDialogOpened.value = false }) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(start = 15.dp, top = 15.dp, end = 15.dp)
        ) {
            //
            // TOP BAR
            //
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
                        text = topAppBarTitle,
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
            CustomDivider()

            //
            // LIST
            //
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .heightIn(min = 50.dp, max = 300.dp)
                    .width(250.dp)
                    .padding(bottom = 10.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isSearchSelected.value) {
                    fields?.filter(predicate)?.forEach { listItem(it) }
                } else {
                    fields?.forEach { listItem(it) }
                }
            }

            CustomDivider()

            //
            // BUTTONS
            //
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) { isDialogOpened.value = false }
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
@ComponentPreview
private fun Preview() {
    val params = TicketData(
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
