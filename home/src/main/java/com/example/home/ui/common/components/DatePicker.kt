package com.example.home.ui.common.components

import android.widget.CalendarView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
internal fun CustomDatePicker(
    date: String,
    isDialogOpened: MutableState<Boolean>,
    onConfirm: (date: String) -> Unit
) {
    if (!isDialogOpened.value) return
    val selectedDate = remember { mutableStateOf(date) }
    Dialog(
        onDismissRequest = { isDialogOpened.value = false },
        properties = DialogProperties()
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(
                    color = MaterialTheme.colors.background,
                    shape = RoundedCornerShape(size = 16.dp)
                )
        ) {
            //
            // TOP APP
            //
            Column(
                Modifier
                    .heightIn(min = 60.dp)
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colors.onBackground,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = "Выбрать дату",
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onPrimary
                )

                Spacer(modifier = Modifier.size(20.dp))

                Text(
                    text = selectedDate.value,
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onPrimary
                )
                Spacer(modifier = Modifier.size(16.dp))
            }

            //
            // CALENDAR
            //
            CustomCalendar(onDateSelected = { selectedDate.value = it })
            Spacer(modifier = Modifier.size(8.dp))

            //
            // BUTTONS
            //
            Row(
                modifier = Modifier
                    .padding(bottom = 16.dp, end = 16.dp)
                    .align(Alignment.End),
            ) {
                Text(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clickable { isDialogOpened.value = false },
                    text = "Отмена",
                    fontSize = MaterialTheme.typography.h3.fontSize,
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.onBackground
                )

                Text(
                    modifier = Modifier
                        .clickable {
                            onConfirm(selectedDate.value)
                            isDialogOpened.value = false
                        },
                    text = "OK",
                    fontSize = MaterialTheme.typography.h3.fontSize,
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.onBackground
                )
            }
        }
    }
}

@Composable
private fun CustomCalendar(onDateSelected: (String) -> Unit) {
    AndroidView(
        modifier = Modifier.wrapContentSize(),
        factory = { CalendarView(it) },
        update = {
            it.minDate = System.currentTimeMillis()
            it.maxDate = System.currentTimeMillis() + 31536000000L  // 1 year
            it.setOnDateChangeListener { _, year, month, dayOfMonth ->
                onDateSelected(
                    LocalDate
                        .now()
                        .withMonth(month + 1)
                        .withYear(year)
                        .withDayOfMonth(dayOfMonth)
                        .toString()
                )
            }
        }
    )
}
