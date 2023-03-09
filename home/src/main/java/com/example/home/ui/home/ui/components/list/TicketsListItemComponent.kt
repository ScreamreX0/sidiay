package com.example.home.ui.home.ui.components.list

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.core.R
import com.example.core.navigation.Screens
import com.example.core.ui.theme.AppTheme
import com.example.core.ui.theme.DefaultTextStyle
import com.example.core.ui.utils.ComponentPreview
import com.example.domain.enums.ticket.TicketPriorityEnum
import com.example.domain.models.entities.TicketEntity

internal class TicketsListItemComponent {
    @Composable
    fun Content(
        navController: NavHostController = rememberNavController(),
        ticket: TicketEntity,
        isDarkTheme: Boolean,
        expanded: MutableState<Boolean> = remember { mutableStateOf(false) }
    ) {
        val textColor = getTextColor(isDarkTheme)
        val surfaceColor = getSurfaceColor(isDarkTheme)
        val context = LocalContext.current

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(if (expanded.value) 240.dp else 130.dp)
                .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
                .shadow(elevation = 2.dp)
        ) {
            ConstraintLayout(
                constraintSet = getConstraints(),
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.98F)
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = 10.dp,
                            bottomStart = 10.dp
                        )
                    )
                    .background(surfaceColor),
            ) {
                /** Constraint layout references */

                /** Number */
                DefaultTextStyle {
                    Text(
                        modifier = Modifier
                            .layoutId("numberRef")
                            .clickable { showHint(context, "Номер заявки") },
                        text = "№${ticket.id}",
                        fontSize = MaterialTheme.typography.h3.fontSize,
                        color = textColor,
                    )
                }

                /** Title */
                DefaultTextStyle {
                    Text(
                        modifier = Modifier
                            .layoutId("titleRef")
                            .clickable { showHint(context, "Заголовок") },
                        text = ticket.name ?: "[Заголовок]",
                        fontSize = MaterialTheme.typography.h4.fontSize,
                        color = textColor,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }

                /** Service */
                Row(
                    modifier = Modifier
                        .layoutId("serviceRef")
                        .clickable { showHint(context, "Сервис") },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    CustomCircle(textColor)
                    Text(
                        modifier = Modifier.padding(start = 5.dp),
                        text = ticket.service ?: "[Сервис не указан]",
                        fontSize = MaterialTheme.typography.h2.fontSize,
                        color = textColor,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                /** Executor */
                Row(
                    modifier = Modifier
                        .layoutId("executorRef")
                        .clickable { showHint(context, "Исполнитель заявки") },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomCircle(textColor)
                    Text(
                        modifier = Modifier.padding(start = 5.dp),
                        text = ticket.executor?.getFullName() ?: "[Исполнитель не назначен]",
                        fontSize = MaterialTheme.typography.h2.fontSize,
                        color = textColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                /** Plane date */
                DefaultTextStyle {
                    Text(
                        modifier = Modifier
                            .layoutId("dateRef")
                            .clickable { showHint(context, "Плановая дата заявки") },
                        text = ticket.plane_date ?: "[Плановая дата не назначена]",
                        fontSize = MaterialTheme.typography.h3.fontSize,
                        color = textColor,
                    )
                }

                /** Update button */
                IconButton(
                    modifier = Modifier.layoutId("updateRef"),
                    onClick = { navController.navigate(Screens.Home.TICKET_UPDATE) }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_create_24),
                        contentDescription = null,
                        tint = textColor
                    )
                }

                /** Status */
                Row(
                    modifier = Modifier
                        .layoutId("statusRef")
                        .fillMaxWidth(0.3F)
                        .clickable { showHint(context, "Статус заявки") },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    CustomCircle(textColor)
                    Text(
                        modifier = Modifier.padding(start = 5.dp),
                        text = ticket.status ?: "[Статус неизвестен]",
                        fontSize = MaterialTheme.typography.h2.fontSize,
                        color = textColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                /** Expand button */
                IconButton(
                    modifier = Modifier.layoutId("expandRef"),
                    onClick = { expanded.value = !expanded.value }) {
                    Icon(
                        painter = painterResource(
                            id = if (expanded.value) {
                                R.drawable.baseline_keyboard_arrow_up_24
                            } else {
                                R.drawable.baseline_keyboard_arrow_down_24
                            }
                        ),
                        contentDescription = null,
                        tint = textColor
                    )
                }

                /** Priority */
                Row(
                    modifier = Modifier
                        .layoutId("priorityRef")
                        .fillMaxWidth(0.3F)
                        .clickable { showHint(context, "Приоритет заявки") },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    CustomCircle(textColor)
                    Text(
                        modifier = Modifier.padding(start = 5.dp),
                        text = TicketPriorityEnum.valueOf(ticket.priority).getName(),
                        fontSize = MaterialTheme.typography.h2.fontSize,
                        color = textColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                /** Expansion */
                if (expanded.value) {
                    /** Divider */
                    Box(
                        modifier = Modifier
                            .layoutId("dividerRef")
                            .height(1.dp)
                            .background(textColor)
                    )

                    /** Author */
                    Row(
                        modifier = Modifier
                            .layoutId("authorRef")
                            .fillMaxWidth(0.4F)
                            .clickable { showHint(context, "Автор заявки") },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        CustomCircle(textColor)
                        Text(
                            modifier = Modifier.padding(start = 5.dp),
                            text = ticket.author?.getFullName() ?: "[Автор не указан]",
                            fontSize = MaterialTheme.typography.h2.fontSize,
                            color = textColor,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    /** Kind */
                    Row(
                        modifier = Modifier
                            .layoutId("kindRef")
                            .fillMaxWidth(0.3F)
                            .clickable { showHint(context, "Вид заявки") },
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        CustomCircle(textColor)
                        Text(
                            modifier = Modifier
                                .padding(start = 5.dp),
                            text = ticket.service ?: "[Вид не указан]",
                            fontSize = MaterialTheme.typography.h2.fontSize,
                            color = textColor,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    /** Terms */
                    Row(
                        modifier = Modifier
                            .layoutId("termsRef")
                            .clickable { showHint(context, "Сроки заявки") },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        CustomCircle(textColor)
                        Text(
                            modifier = Modifier.padding(start = 5.dp),
                            text = "${ticket.creation_date ?: "[Дата создания не указана]"} " +
                                    "- ${ticket.expiration_date ?: "[Дата окончания не указана]"}",
                            fontSize = MaterialTheme.typography.h2.fontSize,
                            color = textColor,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    /** Objects */
                    Row(
                        modifier = Modifier
                            .layoutId("objectsRef")
                            .clickable { showHint(context, "Объекты") },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        CustomCircle(textColor)
                        Text(
                            modifier = Modifier.padding(start = 5.dp),
                            text = ticket.facilities?.joinToString {
                                "${it.name} "
                            } ?: "[Объекты не указаны]",
                            fontSize = MaterialTheme.typography.h2.fontSize,
                            color = textColor,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    /** Completed work */
                    Row(
                        modifier = Modifier
                            .layoutId("completedWorkRef")
                            .clickable { showHint(context, "Завершенная работа") },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        CustomCircle(textColor)
                        Text(
                            modifier = Modifier.padding(start = 5.dp),
                            text = ticket.completed_work ?: "[Нет завершенных работ]",
                            fontSize = MaterialTheme.typography.h2.fontSize,
                            color = textColor,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    /** Description */
                    Row(
                        modifier = Modifier
                            .layoutId("descriptionRef")
                            .clickable { showHint(context, "Описание") },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        CustomCircle(textColor)
                        Text(
                            modifier = Modifier.padding(start = 5.dp),
                            text = ticket.description ?: "[Нет описания]",
                            fontSize = MaterialTheme.typography.h2.fontSize,
                            color = textColor,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            StatusBar(
                modifier = Modifier.weight(0.02F),
                isDarkTheme = isDarkTheme,
                ticket.priority
            )
        }
    }

    private fun getConstraints() = ConstraintSet {
        // Top
        val numberRef = createRefFor("numberRef")
        val titleRef = createRefFor("titleRef")

        // Shrink
        val serviceRef = createRefFor("serviceRef")
        val executorRef = createRefFor("executorRef")
        val dateRef = createRefFor("dateRef")
        val priorityRef = createRefFor("priorityRef")
        val statusRef = createRefFor("statusRef")
        val updateRef = createRefFor("updateRef")
        val expandRef = createRefFor("expandRef")

        // Expanded
        val dividerRef = createRefFor("dividerRef")
        val kindRef = createRefFor("kindRef")
        val authorRef = createRefFor("authorRef")
        val termsRef = createRefFor("termsRef")
        val objectsRef = createRefFor("objectsRef")
        val completedWorkRef = createRefFor("completedWorkRef")
        val descriptionRef = createRefFor("descriptionRef")

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
            end.linkTo(parent.end)
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
            end.linkTo(parent.end)
        }
        constrain(priorityRef) {
            top.linkTo(executorRef.top)
            bottom.linkTo(executorRef.bottom)
            end.linkTo(expandRef.start)
            height = Dimension.fillToConstraints
        }

        // Expanded
        constrain(dividerRef) {
            top.linkTo(executorRef.bottom, margin = 5.dp)
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
            top.linkTo(dividerRef.bottom, margin = 6.dp)
            start.linkTo(executorRef.start)
            end.linkTo(authorRef.start, margin = 10.dp)
            width = Dimension.fillToConstraints
        }
        constrain(termsRef) {
            top.linkTo(kindRef.bottom, margin = 6.dp)
            start.linkTo(kindRef.start)
            end.linkTo(parent.end, margin = 10.dp)
            width = Dimension.fillToConstraints
        }
        constrain(objectsRef) {
            top.linkTo(termsRef.bottom, margin = 6.dp)
            start.linkTo(termsRef.start)
            end.linkTo(parent.end, margin = 10.dp)
            width = Dimension.fillToConstraints
        }
        constrain(completedWorkRef) {
            top.linkTo(objectsRef.bottom, margin = 6.dp)
            start.linkTo(objectsRef.start)
            end.linkTo(parent.end, margin = 10.dp)
            width = Dimension.fillToConstraints
        }
        constrain(descriptionRef) {
            top.linkTo(completedWorkRef.bottom, margin = 6.dp)
            start.linkTo(completedWorkRef.start)
            end.linkTo(parent.end, margin = 10.dp)
            width = Dimension.fillToConstraints
        }
    }

    private fun showHint(context: Context, text: String) {
        Toast.makeText(
            context,
            text,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun getSurfaceColor(isDarkTheme: Boolean) =
        if (isDarkTheme) Color(0XFF464343) else Color.Black.copy(alpha = 0.01F)

    private fun getTextColor(isDarkTheme: Boolean) = if (isDarkTheme) Color.White else Color.Black

    /** Status bar */
    @Composable
    private fun StatusBar(modifier: Modifier, isDarkTheme: Boolean, priority: Long?) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .fillMaxWidth(0.02F)
                .clip(
                    shape = RoundedCornerShape(
                        topEnd = 25.dp,
                        bottomEnd = 25.dp
                    )
                )
                .background(getStatusBarColor(isDarkTheme, priority)),
        ) {}
    }

    private fun getStatusBarColor(isDarkTheme: Boolean, priority: Long?): Color {
        return if (isDarkTheme) {
            when (priority) {
                5L -> Color(0XFF610000)
                4L -> Color(0XFF5F6100)
                3L -> Color(0XFF224487)
                2L -> Color(0XFF00610A)
                else -> Color(0XFF8F8F8F)
            }
        } else {
            when (priority) {
                5L -> Color(0XFFFF4F4F)
                4L -> Color(0XFFDADE00)
                3L -> Color(0XFF4764CB)
                2L -> Color(0XFF00D215)
                else -> Color(0XFFA6A6A6)
            }
        }
    }

    /** Circle */
    @Composable
    private fun CustomCircle(textColor: Color) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(textColor)
        )
    }

    /** Preview */
    @Composable
    @ComponentPreview
    private fun ContentPreview() {
        AppTheme(isSystemInDarkTheme()) {
            Content(isDarkTheme = false, ticket = TicketEntity())
        }
    }
}