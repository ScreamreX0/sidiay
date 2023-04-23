package com.example.home.ui.common

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.core.R
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.TicketData
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.data_classes.params.TicketRestriction
import com.example.domain.enums.TicketFieldsEnum

internal interface ITicketField {
    val ticketFieldsParams: MutableState<TicketFieldParams>
    val notIntractableAlpha: Float get() = 0.8F
    val ticketData: MutableState<TicketData?> get() = mutableStateOf(null)
    val field: TicketFieldsEnum
    val ticket: MutableState<TicketEntity>
    val ticketRestrictions: TicketRestriction
    val isValueNull: Boolean

    fun init(thisField: ITicketField, ticketRestrictions: TicketRestriction, isValueNull: Boolean) {
        val ticketFieldInRequiredFields = field in ticketRestrictions.requiredFields
        val ticketFieldInAllowedFields = field in ticketRestrictions.allowedFields

        thisField.ticketFieldsParams.value = TicketFieldParams(
            starred = ticketFieldInRequiredFields,
            isClickable = ticketFieldInRequiredFields || ticketFieldInAllowedFields,
            isVisible = !isValueNull || ticketFieldInRequiredFields || ticketFieldInAllowedFields,
        )
    }

    @Composable
    fun TicketFieldComponent(
        title: String,
        icon: Int,
        item: @Composable () -> Unit,
        ticketFieldsParams: TicketFieldParams
    ) {
        val modifier = if (ticketFieldsParams.isClickable) Modifier else Modifier.alpha(0.6F)
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 25.dp, start = 25.dp, end = 25.dp)
        ) {
            Icon(
                modifier = modifier
                    .height(45.dp)
                    .width(45.dp)
                    .padding(end = 10.dp),
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = MaterialTheme.colors.onBackground
            )
            Column {
                Row {
                    Text(
                        modifier = modifier.padding(bottom = 5.dp),
                        text = title,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground,
                        fontSize = MaterialTheme.typography.h3.fontSize
                    )
                    if (ticketFieldsParams.starred) {
                        Text(
                            modifier = modifier.padding(start = 5.dp),
                            text = "*",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.onBackground,
                            fontSize = MaterialTheme.typography.h3.fontSize
                        )
                    }
                }
                item.invoke()
            }
        }
    }

    @Composable
    fun <T> CustomDialog(
        isDialogOpened: MutableState<Boolean>,
        topAppBarTitle: String,
        isSearchSelected: MutableState<Boolean> = remember { mutableStateOf(false) },
        scrollState: ScrollState,
        fields: List<T>?,
        predicate: (T, TextFieldValue) -> Boolean,
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
                                    modifier = Modifier.clickable { isSearchSelected.value = false },
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
                                ) { isSearchSelected.value = true },
                            painter = painterResource(id = R.drawable.ic_baseline_search_24_white),
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
                        fields?.filter(searchTextState, predicate)?.forEach { listItem(it) }
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
    fun ListElement(title: String, onClick: () -> Unit) {
        Text(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .clickable { onClick() },
            text = title,
            color = MaterialTheme.colors.onBackground,
            fontSize = MaterialTheme.typography.h3.fontSize,
        )
    }

    @Composable
    fun CustomText(
        modifier: Modifier = Modifier,
        label: String,
        onClick: () -> Unit = {},
    ) {
        Text(
            modifier = modifier
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) { onClick() },
            text = label,
            fontSize = MaterialTheme.typography.h5.fontSize,
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.8F),
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

    private fun <T> Iterable<T>?.filter(
        searchTextState: MutableState<TextFieldValue>,
        predicate: (T, TextFieldValue) -> Boolean
    ): List<T> {
        val newList = ArrayList<T>()
        this?.let {
            for (elem in it) {
                if (predicate(elem, searchTextState.value)) {
                    newList.add(elem)
                }
            }
        }
        return newList
    }
}