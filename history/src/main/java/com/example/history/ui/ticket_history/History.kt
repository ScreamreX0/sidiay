package com.example.history.ui.ticket_history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.core.R
import com.example.core.ui.theme.AppTheme
import com.example.core.utils.Logger
import com.example.core.utils.ScreenPreview
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.enums.KindsEnum
import com.example.domain.enums.PrioritiesEnum
import com.example.domain.enums.ServicesEnum


class History {

    @Composable
    fun HistoryScreen(ticket: TicketEntity = TicketEntity()) { Content(ticket = ticket) }

    @Composable
    fun Content(ticket: TicketEntity = TicketEntity()) {
        val mainScrollableState = rememberScrollState()
        Column(
            modifier = Modifier
                .verticalScroll(mainScrollableState)
        ) {
            TopBar()
            ListItemText(ticket.id, { it.toString() }, "Номер")
            ListItemText(ticket.status, { it.toString() }, "Статус")
            ListItemText(ticket.author, { it.getFullName() }, "Автор")
            ListItemText(ticket.field, { it.name }, "Участок")
            ListItemText(ticket.dispatcher, { it.getFullName() }, "Диспетчер")
            ListItemText(ticket.executors_nominator, { it.getFullName() }, "Назначивший исполнителя")
            ListItemText(ticket.quality_controllers_nominator, { it.getFullName() }, "Назначивший специалиста по контролю качества")
            ListItemText(ticket.creation_date, { it }, "Дата создания")
            ListItemText(ticket.ticket_name, { it }, "Название заявки")
            ListItemText(ticket.description_of_work, { it }, "Описание")
            ListItemText(ticket.kind, { KindsEnum.getByValue(it)?.label }, "Вид")
            ListItemText(ticket.service, { ServicesEnum.getByValue(it)?.label }, "Сервис")
            ListItemIterable(ticket.facilities, { it.name ?: "Объект №${it.id}" }, "Объекты")
            ListItemIterable(ticket.equipment, { it.name ?: "Оборудование №${it.id}" }, "Оборудование")
            ListItemIterable(ticket.transport, { it.name ?: "Транспорт №${it.id}" }, "Транспорт")
            ListItemText(ticket.priority, { PrioritiesEnum.getByValue(it)?.label }, "Приоритет")
            ListItemText(ticket.assessed_value, { it.toString() }, "Оценочная стоимость")
            ListItemText(ticket.assessed_value_description, { it }, "Описание оценочной стоимости")
            ListItemText(ticket.reason_for_cancellation, { it }, "Причины отмены")
            ListItemText(ticket.reason_for_rejection, { it }, "Причины отклонения заявки")
            ListItemText(ticket.execution_problem_description, { it }, "Проблемы при исполнении")
            ListItemIterable(ticket.executors, { it.getFullName() }, "Исполнители")
            ListItemText(ticket.plane_date, { it }, "Плановая дата")
            ListItemText(ticket.reason_for_suspension, { it }, "Причины приостановки")
            ListItemText(ticket.completed_work, { it }, "Выполненная работа")
            ListItemIterable(ticket.quality_controllers, { it.getFullName() }, "Специалисты по контролю качества")
            ListItemText(ticket.improvement_comment, { it }, "Причины доработки")
            ListItemText(ticket.closing_date, { it }, "Дата закрытия")
        }
    }

    @Composable
    private fun TopBar() {
        Row(
            modifier = Modifier
                .height(80.dp)
                .padding(bottom = 20.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colors.onBackground),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                text = "Заявка",
                fontSize = MaterialTheme.typography.h4.fontSize,
                color = MaterialTheme.colors.background
            )
        }
    }

    @Composable
    private fun ListItem(text: String, itemLabel: String) {
        Row {
            Text(
                modifier = Modifier.padding(start = 24.dp, bottom = 16.dp),
                text = "$itemLabel -> $text",
                color = MaterialTheme.colors.onBackground,
                fontSize = MaterialTheme.typography.h5.fontSize,
                overflow = TextOverflow.Visible,
            )
        }
    }

    @Composable
    private fun <T> ListItemText(item: T?, text: (T) -> String?, itemLabel: String) =
        item?.let { text(it)?.let { itLabel -> ListItem(itLabel, itemLabel) } }

    @Composable
    private fun <T> ListItemIterable(items: List<T>?, text: (T) -> String, itemLabel: String) {
        if (!items.isNullOrEmpty()) ListItem(items.joinToString { text(it) }, itemLabel)
    }


    @ScreenPreview
    @Composable
    private fun Preview() {
        AppTheme(isSystemInDarkTheme()) { Content() }
    }
}