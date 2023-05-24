package com.example.subscriptions.ui.subscriptions_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.core.ui.theme.AppTheme
import com.example.core.utils.ComponentPreview
import com.example.domain.data_classes.entities.TicketEntity
import com.example.notifications.R

class SubscriptionsList {
    @Composable
    internal fun NotificationsListScreen(
        tickets: List<TicketEntity>? = List(10) { TicketEntity(id = it.toLong()) },
        onClick: (TicketEntity) -> Unit = { _ -> },
        emptyListTitle: String = "Пусто",
    ) {
        if (tickets.isNullOrEmpty()) {
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

        LazyColumn(
            modifier = Modifier
                .background(MaterialTheme.colors.background.copy(alpha = 0.9F))
                .fillMaxSize(),
            userScrollEnabled = true,
        ) {
            items(tickets.size) { index ->
                NotificationsListItem(
                    ticket = tickets[index],
                    onClick = onClick,
                )
            }
        }
    }

    @Composable
    private fun NotificationsListItem(
        ticket: TicketEntity = TicketEntity(),
        onClick: (TicketEntity) -> Unit = { _ -> },
    ) {
        Row(
            modifier = Modifier
                .height(70.dp)
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .shadow(elevation = 3.dp, shape = RoundedCornerShape(8.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = ticket.id.toString(),
                    fontSize = MaterialTheme.typography.h3.fontSize,
                    color = MaterialTheme.colors.onBackground,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }

            Box(modifier = Modifier.fillMaxWidth(0.7F)) {
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = ticket.ticket_name ?: "[Название заявки]",
                    fontSize = MaterialTheme.typography.h3.fontSize,
                    color = MaterialTheme.colors.onBackground,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(ticket) }
                    .padding(end = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    painterResource(id = com.example.core.R.drawable.baseline_notifications_off_24),
                    contentDescription = null,
                    tint = MaterialTheme.colors.onBackground,
                )
            }
        }
    }

    @Composable
    @ComponentPreview
    private fun ContentPreview() {
        AppTheme(isSystemInDarkTheme()) { NotificationsListItem() }
    }
}