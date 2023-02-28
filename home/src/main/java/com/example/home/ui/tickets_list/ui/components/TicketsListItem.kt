package com.example.home.ui.tickets_list.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.core.ui.theme.AppTheme
import com.example.core.ui.theme.DefaultTextStyle

class TicketsListItem {
    companion object {
        @Composable
        internal fun Content(isDarkTheme: Boolean) {
            val surfaceColor = if (isDarkTheme) {
                Color(0XFF464343)
            } else {
                Color.Black.copy(alpha = 0.01F)
            }
            val textColor = if (isDarkTheme) {
                Color.White
            } else {
                Color.Black
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
                    .shadow(elevation = 1.dp)
            ) {
                ConstraintLayout(
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
                    val (
                        numberRef,
                        titleRef,
                        serviceRef,
                        executorRef,
                        dateRef,
                        priorityRef,
                        statusRef,
                    ) = createRefs()

                    // Number
                    DefaultTextStyle {
                        Text(
                            modifier = Modifier
                                .constrainAs(numberRef) {
                                    top.linkTo(parent.top, 10.dp)
                                    start.linkTo(parent.start, margin = 10.dp)
                                }
                                .fillMaxHeight(0.15F),
                            text = "№1000",
                            fontSize = MaterialTheme.typography.h3.fontSize,
                            color = textColor,
                        )
                    }

                    // Title
                    DefaultTextStyle {
                        Text(
                            modifier = Modifier
                                .constrainAs(titleRef) {
                                    top.linkTo(numberRef.bottom)
                                    start.linkTo(numberRef.start)
                                    end.linkTo(parent.end)
                                    width = Dimension.fillToConstraints
                                }
                                .fillMaxHeight(0.25F),
                            text = "Заголовокqweqweqweqweqweqweqwe",
                            fontSize = MaterialTheme.typography.h4.fontSize,
                            color = textColor,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }

                    // Service
                    Row(
                        modifier = Modifier
                            .constrainAs(serviceRef) {
                                top.linkTo(titleRef.bottom)
                                start.linkTo(titleRef.start)
                                end.linkTo(statusRef.start, margin = 10.dp)
                                width = Dimension.fillToConstraints
                            }
                            .fillMaxHeight(0.2F),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        CustomCircle(textColor)
                        Text(
                            modifier = Modifier
                                .padding(start = 5.dp),
                            text = "Энергоqweqweqweqwqweqweqweqweqweqweq",
                            fontSize = MaterialTheme.typography.h2.fontSize,
                            color = textColor,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    // Field/executor
                    Row(
                        modifier = Modifier
                            .constrainAs(executorRef) {
                                top.linkTo(serviceRef.bottom)
                                start.linkTo(serviceRef.start)
                                bottom.linkTo(parent.bottom, margin = 10.dp)
                                end.linkTo(priorityRef.start, margin = 10.dp)
                                width = Dimension.fillToConstraints
                            }
                            .fillMaxHeight(0.2F),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CustomCircle(textColor)
                        Text(
                            modifier = Modifier
                                .padding(start = 5.dp),
                            text = "Участокqweqweqweqweqweqwe",
                            fontSize = MaterialTheme.typography.h2.fontSize,
                            color = textColor,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    // Date
                    DefaultTextStyle {
                        Text(
                            modifier = Modifier
                                .constrainAs(dateRef) {
                                    top.linkTo(numberRef.top)
                                    end.linkTo(parent.end, margin = 10.dp)
                                },
                            text = "27.02.23 (Вс)",
                            fontSize = MaterialTheme.typography.h3.fontSize,
                            color = textColor,
                        )
                    }

                    // Status
                    Row(
                        modifier = Modifier
                            .constrainAs(statusRef) {
                                top.linkTo(serviceRef.top)
                                bottom.linkTo(serviceRef.bottom)
                                end.linkTo(dateRef.end)
                                height = Dimension.fillToConstraints
                            }
                            .fillMaxWidth(0.3F),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        CustomCircle(textColor)
                        Text(
                            modifier = Modifier
                                .padding(start = 5.dp),
                            text = "Статусjhkhkjhjkhkjhkjkjkjhkjhkjh",
                            fontSize = MaterialTheme.typography.h2.fontSize,
                            color = textColor,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    // Priority
                    Row(
                        modifier = Modifier
                            .constrainAs(priorityRef) {
                                top.linkTo(executorRef.top)
                                bottom.linkTo(executorRef.bottom)
                                end.linkTo(statusRef.end)
                                height = Dimension.fillToConstraints
                            }
                            .fillMaxWidth(0.3F),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        CustomCircle(textColor)
                        Text(
                            modifier = Modifier
                                .padding(start = 5.dp),
                            text = "Приоритет",
                            fontSize = MaterialTheme.typography.h2.fontSize,
                            color = textColor,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,

                            )
                    }
                }

                StatusBar(
                    modifier = Modifier
                        .weight(0.02F),
                    isDarkTheme = isDarkTheme,
                )
            }
        }

        /** Status bar */
        @Composable
        private fun StatusBar(modifier: Modifier, isDarkTheme: Boolean) {
            val statusColor = if (isDarkTheme) Color(0XFF610000) else Color(0xFFFF4F4F)
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
                    .background(statusColor),
            ) {}
        }

        /** Other */
        @Composable
        private fun CustomCircle(textColor: Color) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(textColor)
            )
        }
    }

    @Composable
    @Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
    private fun ContentPreview() {
        AppTheme {
            Content(false)
        }
    }
}