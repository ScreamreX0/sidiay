package com.example.home.ui.ticket_create.components

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

class DatePicker {
    @Composable
    internal fun DatePickerDialog(
        date: MutableState<LocalDate?>,
        isDialogOpened: MutableState<Boolean>,
    ) {
        val formatter = remember { DateTimeFormatter.ofPattern("dd.MM.yyyy") }
        val selectedDate = remember { mutableStateOf(date.value ?: LocalDate.now()) }
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
                        text = selectedDate.value.format(formatter),
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
                                date.value = selectedDate.value
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
    fun CustomCalendar(onDateSelected: (LocalDate) -> Unit) {
        AndroidView(
            modifier = Modifier.wrapContentSize(),
            factory = { CalendarView(it) },
            update = {
                it.minDate = System.currentTimeMillis()
                it.maxDate =
                    System.currentTimeMillis() + 31536000000L  // 31B milliseconds is 1 year

                it.setOnDateChangeListener { _, year, month, dayOfMonth ->
                    onDateSelected(
                        LocalDate
                            .now()
                            .withMonth(month + 1)
                            .withYear(year)
                            .withDayOfMonth(dayOfMonth)
                    )
                }
            }
        )
    }
}