package com.example.home.ui.tickets_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.example.core.R
import com.example.core.ui.theme.AppTheme
import com.example.core.ui.theme.CustomColors
import com.example.core.utils.ComponentPreview
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.AuthParams
import com.example.domain.enums.TicketStatuses
import java.time.format.DateTimeFormatter

@Composable
internal fun MenuTicketList(
    authParams: AuthParams? = AuthParams(),
    tickets: MutableState<List<TicketEntity>?> = mutableStateOf(listOf()),
    refreshing: MutableState<Boolean> = mutableStateOf(false),
    onClickUpdate: (TicketEntity) -> Unit = { _ -> },
    emptyListTitle: String = "Пусто",
    isDraftsList: Boolean = false,
    deleteDraft: (TicketEntity) -> Unit = { _ -> }
) {
    if (tickets.value == null || tickets.value?.size == 0) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = emptyListTitle,
                fontSize = MaterialTheme.typography.h4.fontSize,
                color = MaterialTheme.colors.onBackground
            )
        }
        return
    }

    if (refreshing.value) {
        Column(Modifier.fillMaxSize()) {
            CircularProgressIndicator()
        }
        return
    }

    LazyColumn(
        modifier = Modifier
            .background(MaterialTheme.colors.background.copy(alpha = 0.9F))
            .fillMaxSize(),
        userScrollEnabled = true,
    ) {
        items(tickets.value!!.size) { index ->
            MenuTicketListItem(
                isDarkMode = authParams?.darkMode ?: false,
                ticket = tickets.value!![index],
                onClickUpdate = onClickUpdate,
                isDraft = isDraftsList,
                deleteDraft = deleteDraft
            )
        }
    }
}

@Composable
private fun MenuTicketListItem(
    ticket: TicketEntity = TicketEntity(),
    isDarkMode: Boolean = false,
    expanded: MutableState<Boolean> = mutableStateOf(false),
    onClickUpdate: (TicketEntity) -> Unit = { _ -> },
    isDraft: Boolean = false,
    deleteDraft: (TicketEntity) -> Unit = { _ -> }
) {
    val textColor = if (isDarkMode) Color.White else CustomColors.Grey780
    val circleColor = CustomColors.Orange700.copy(alpha = 0.8F)
    val dividerColor = CustomColors.Orange700.copy(alpha = 0.8F)
    val surfaceColor = if (isDarkMode) {
        MaterialTheme.colors.background.copy(alpha = 0.8F)
    } else {
        MaterialTheme.colors.background
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 5.dp, top = 5.dp)
            .shadow(elevation = 3.dp)
            .clickable { expanded.value = !expanded.value }
    ) {
        val defaultTextSize: TextUnit = MaterialTheme.typography.h2.fontSize

        ConstraintLayout(
            constraintSet = getConstraints(expanded = expanded),
            modifier = Modifier
                .fillMaxWidth()
                .background(surfaceColor)
                .clip(RoundedCornerShape(10.dp)),
        ) {
            // Number
            ItemText(
                modifier = Modifier.layoutId("numberRef"),
                text = "№${ticket.id}",
                circleColor = circleColor,
                textColor = textColor,
                fontSize = defaultTextSize.times(1.3),
                isCircleEnabled = false,
            )

            // Title
            ItemText(
                modifier = Modifier.layoutId("titleRef"),
                text = ticket.name ?: "[Заголовок]",
                circleColor = circleColor,
                textColor = textColor,
                fontSize = defaultTextSize.times(1.6),
                isCircleEnabled = false,
            )

            // Service
            ItemText(
                modifier = Modifier.layoutId("serviceRef"),
                text = ticket.service?.name ?: "[Сервис не указан]",
                circleColor = circleColor,
                textColor = textColor,
                fontSize = defaultTextSize,
            )

            // Executor
            ItemText(
                modifier = Modifier
                    .layoutId("executorRef")
                    .padding(bottom = 10.dp),
                text = ticket.executor?.getFullName() ?: "[Исполнитель не назначен]",
                circleColor = circleColor,
                textColor = textColor,
                fontSize = defaultTextSize,
            )

            // Plane date
            ItemText(
                modifier = Modifier.layoutId("dateRef"),
                text = ticket.plane_date?.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                    ?: "[Плановая дата не назначена]",
                circleColor = circleColor,
                textColor = textColor,
                fontSize = defaultTextSize,
                isCircleEnabled = false
            )

            // Update button
            Icon(
                modifier = Modifier
                    .layoutId("updateRef")
                    .padding(start = 5.dp)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) { onClickUpdate(ticket) },
                painter = painterResource(id = R.drawable.baseline_create_24),
                contentDescription = null,
                tint = CustomColors.Orange700,
            )

            // Status
            ItemText(
                modifier = Modifier
                    .layoutId("statusRef")
                    .fillMaxWidth(0.3F),
                text = TicketStatuses.get(ticket.status)?.title ?: "[Статус неизвестен]",
                circleColor = circleColor,
                textColor = textColor,
                fontSize = defaultTextSize,
            )

            if (isDraft) {
                // Trash button
                Icon(
                    modifier = Modifier
                        .layoutId("trashCanRef")
                        .padding(start = 5.dp)
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null
                        ) { deleteDraft(ticket) },
                    painter = painterResource(id = R.drawable.baseline_restore_from_trash_24),
                    contentDescription = null,
                    tint = CustomColors.Orange700,
                )
            }

            // Expand icon
            Icon(
                modifier = Modifier
                    .layoutId("expandRef")
                    .padding(bottom = 10.dp),
                painter = painterResource(
                    id = if (expanded.value) {
                        R.drawable.baseline_keyboard_arrow_up_24
                    } else {
                        R.drawable.baseline_keyboard_arrow_down_24
                    }
                ),
                contentDescription = "Expand ticket",
                tint = CustomColors.Orange700,
            )

            // Priority
            ItemText(
                modifier = Modifier
                    .layoutId("priorityRef")
                    .padding(bottom = 10.dp)
                    .fillMaxWidth(0.3F),
                text = ticket.priority?.name ?: "Неизвестный",
                circleColor = circleColor,
                textColor = getPriorityColor(isDarkMode, ticket.priority?.value ?: 1),
                fontSize = defaultTextSize,
            )

            // Expansion
            if (expanded.value) {
                // Divider
                Box(
                    modifier = Modifier
                        .layoutId("dividerRef")
                        .height(1.dp)
                        .background(dividerColor)
                )

                // Author
                ItemText(
                    modifier = Modifier
                        .layoutId("authorRef")
                        .fillMaxWidth(0.4F),
                    text = ticket.author?.getFullName() ?: "[Автор не указан]",
                    circleColor = circleColor,
                    textColor = textColor,
                    fontSize = defaultTextSize,
                )

                // Kind
                ItemText(
                    modifier = Modifier
                        .layoutId("kindRef")
                        .fillMaxWidth(0.3F),
                    text = ticket.service?.name ?: "[Вид не указан]",
                    circleColor = circleColor,
                    textColor = textColor,
                    fontSize = defaultTextSize,
                )

                // Terms
                ItemText(
                    modifier = Modifier.layoutId("termsRef"),
                    text = "${ticket.creation_date ?: "[Дата создания не указана]"} " +
                            "- ${ticket.closing_date ?: "[Дата окончания не указана]"}",
                    circleColor = circleColor,
                    textColor = textColor,
                    fontSize = defaultTextSize,
                )

                // Objects
                ItemText(
                    modifier = Modifier.layoutId("objectsRef"),
                    text = ticket.facilities?.joinToString { "${it.name} " }
                        ?: "[Объекты не указаны]",
                    circleColor = circleColor,
                    textColor = textColor,
                    fontSize = defaultTextSize,
                )

                // Completed work
                ItemText(
                    modifier = Modifier.layoutId("completedWorkRef"),
                    text = ticket.completed_work ?: "[Нет завершенных работ]",
                    circleColor = circleColor,
                    textColor = textColor,
                    fontSize = defaultTextSize,
                )

                // Description
                ItemText(
                    modifier = Modifier.layoutId("descriptionRef"),
                    text = ticket.description ?: "[Нет описания]",
                    circleColor = circleColor,
                    textColor = textColor,
                    fontSize = defaultTextSize,
                )
            }
        }
    }
}

@Composable
private fun ItemText(
    modifier: Modifier,
    text: String,
    circleColor: Color,
    isCircleEnabled: Boolean = true,
    textColor: Color,
    fontSize: TextUnit,
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (isCircleEnabled) CustomCircle(circleColor)
        Text(
            modifier = Modifier.padding(start = 5.dp),
            text = text,
            fontSize = fontSize,
            color = textColor,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

private fun getConstraints(expanded: MutableState<Boolean>) = ConstraintSet {
    val numberRef = createRefFor("numberRef")
    val titleRef = createRefFor("titleRef")
    val serviceRef = createRefFor("serviceRef")
    val executorRef = createRefFor("executorRef")
    val dateRef = createRefFor("dateRef")
    val priorityRef = createRefFor("priorityRef")
    val statusRef = createRefFor("statusRef")
    val updateRef = createRefFor("updateRef")
    val expandRef = createRefFor("expandRef")
    val trashCanRef = createRefFor("trashCanRef")
    // Top
    constrain(numberRef) {
        top.linkTo(parent.top, 10.dp)
        start.linkTo(parent.start, margin = 10.dp)
    }
    constrain(titleRef) {
        top.linkTo(numberRef.bottom)
        start.linkTo(numberRef.start)
        end.linkTo(parent.end)
        width = Dimension.fillToConstraints
    }

    // Shrink
    constrain(serviceRef) {
        top.linkTo(titleRef.bottom, margin = 5.dp)
        start.linkTo(titleRef.start)
        end.linkTo(statusRef.start, margin = 10.dp)
        width = Dimension.fillToConstraints
    }
    constrain(executorRef) {
        top.linkTo(serviceRef.bottom, margin = 10.dp)
        start.linkTo(serviceRef.start)
        end.linkTo(priorityRef.start, margin = 10.dp)
        width = Dimension.fillToConstraints
    }
    constrain(dateRef) {
        top.linkTo(numberRef.top)
        end.linkTo(parent.end, margin = 10.dp)
    }
    constrain(updateRef) {
        top.linkTo(serviceRef.top)
        bottom.linkTo(serviceRef.bottom)
        end.linkTo(parent.end, margin = 10.dp)
    }
    constrain(trashCanRef) {
        bottom.linkTo(updateRef.top)
        end.linkTo(parent.end, margin = 10.dp)
    }
    constrain(statusRef) {
        top.linkTo(serviceRef.top)
        bottom.linkTo(serviceRef.bottom)
        end.linkTo(updateRef.start)
        height = Dimension.fillToConstraints
    }
    constrain(expandRef) {
        top.linkTo(executorRef.top)
        bottom.linkTo(executorRef.bottom)
        end.linkTo(updateRef.end)
    }
    constrain(priorityRef) {
        top.linkTo(executorRef.top)
        bottom.linkTo(executorRef.bottom)
        end.linkTo(expandRef.start)
        height = Dimension.fillToConstraints
    }

    if (!expanded.value) {
        return@ConstraintSet
    }

    // Expanded
    val dividerRef = createRefFor("dividerRef")
    val kindRef = createRefFor("kindRef")
    val authorRef = createRefFor("authorRef")
    val termsRef = createRefFor("termsRef")
    val objectsRef = createRefFor("objectsRef")
    val completedWorkRef = createRefFor("completedWorkRef")
    val descriptionRef = createRefFor("descriptionRef")

    constrain(dividerRef) {
        top.linkTo(executorRef.bottom)
        start.linkTo(executorRef.start)
        end.linkTo(parent.end, margin = 10.dp)
        width = Dimension.fillToConstraints
    }
    constrain(authorRef) {
        top.linkTo(kindRef.top)
        bottom.linkTo(kindRef.bottom)
        end.linkTo(parent.end, margin = 10.dp)
        height = Dimension.fillToConstraints
    }
    constrain(kindRef) {
        top.linkTo(dividerRef.bottom, margin = 10.dp)
        start.linkTo(executorRef.start)
        end.linkTo(authorRef.start, margin = 10.dp)
        width = Dimension.fillToConstraints
    }
    constrain(termsRef) {
        top.linkTo(kindRef.bottom, margin = 10.dp)
        start.linkTo(kindRef.start)
        end.linkTo(parent.end, margin = 10.dp)
        width = Dimension.fillToConstraints
    }
    constrain(objectsRef) {
        top.linkTo(termsRef.bottom, margin = 10.dp)
        start.linkTo(termsRef.start)
        end.linkTo(parent.end, margin = 10.dp)
        width = Dimension.fillToConstraints
    }
    constrain(completedWorkRef) {
        top.linkTo(objectsRef.bottom, margin = 10.dp)
        start.linkTo(objectsRef.start)
        end.linkTo(parent.end, margin = 10.dp)
        width = Dimension.fillToConstraints
    }
    constrain(descriptionRef) {
        top.linkTo(completedWorkRef.bottom, margin = 10.dp)
        start.linkTo(completedWorkRef.start)
        end.linkTo(parent.end, margin = 10.dp)
        bottom.linkTo(parent.bottom, margin = 10.dp)
        width = Dimension.fillToConstraints
    }
}

private fun getPriorityColor(isDarkTheme: Boolean, priority: Int): Color {
    return if (isDarkTheme) {
        when (priority) {
            5 -> Color(0xFFC51111)
            4 -> Color(0xFFA9AC1B)
            3 -> Color(0xFF376DD6)
            2 -> Color(0xFF0A9E19)
            else -> Color(0XFF8F8F8F)
        }
    } else {
        when (priority) {
            5 -> Color(0XFFFF4F4F)
            4 -> Color(0xFF9DA000)
            3 -> Color(0XFF4764CB)
            2 -> Color(0XFF00D215)
            else -> Color(0XFFA6A6A6)
        }
    }
}

@Composable
private fun CustomCircle(color: Color) {
    Box(
        modifier = Modifier
            .size(10.dp)
            .clip(CircleShape)
            .background(color)
    )
}

@Composable
@ComponentPreview
private fun ContentPreview() {
    AppTheme(isSystemInDarkTheme()) { MenuTicketListItem() }
}
