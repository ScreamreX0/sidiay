package com.example.home.ui.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.R
import com.example.core.ui.components.CustomAlertDialog
import com.example.core.ui.theme.AppTheme
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.AuthParams

@Composable
internal fun TicketCreateBottomBar(
    modifier: Modifier = Modifier,
    authParams: AuthParams = AuthParams(),
    bottomBarSelectable: MutableState<Boolean> = mutableStateOf(true),
    saveTicket: () -> Unit = { },
    saveDraft: () -> Unit = { }
) {
    Row(modifier = modifier.height(50.dp)) {
        if (!authParams.connectionParams?.url.isNullOrEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.5F)
                    .background(MaterialTheme.colors.onBackground)
                    .clickable { if (bottomBarSelectable.value) saveTicket() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = "Сохранить",
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(MaterialTheme.colors.onBackground)
                .clickable { if (bottomBarSelectable.value) saveDraft() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = "Черновик",
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@Composable
internal fun TicketUpdateBottomBar(
    modifier: Modifier = Modifier,
    authParams: AuthParams = AuthParams(),
    bottomBarSelectable: MutableState<Boolean>,
    updateTicket: () -> Unit = { },
    saveDraft: () -> Unit = { }
) {
    Row(modifier = modifier.height(50.dp)) {
        if (!authParams.connectionParams?.url.isNullOrEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.5F)
                    .background(MaterialTheme.colors.onBackground)
                    .clickable { if (bottomBarSelectable.value) updateTicket() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = "Сохранить",
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(MaterialTheme.colors.onBackground)
                .clickable { if (bottomBarSelectable.value) saveDraft() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = "Черновик",
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@Composable
internal fun TicketCreateTopBar(
    modifier: Modifier = Modifier,
    iconsVisible: MutableState<Boolean> = remember { mutableStateOf(true) },
    clearFieldsDialogOpened: MutableState<Boolean> = remember { mutableStateOf(false) },
    ticket: MutableState<TicketEntity>,
    navigateToBack: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .height(65.dp)
            .background(MaterialTheme.colors.onBackground),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            modifier = Modifier
                .padding(start = 15.dp)
                .fillMaxHeight()
                .width(30.dp)
                .clickable { navigateToBack() },
            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
            contentDescription = "Back",
            tint = MaterialTheme.colors.onPrimary,
        )

        if (iconsVisible.value) {
            Row {
                Icon(
                    modifier = Modifier
                        .padding(end = 30.dp)
                        .fillMaxHeight()
                        .width(30.dp)
                        .clickable { clearFieldsDialogOpened.value = true },
                    painter = painterResource(id = R.drawable.baseline_format_clear_24),
                    contentDescription = "Clear all fields",
                    tint = MaterialTheme.colors.onPrimary,
                )
            }
        }

        if (clearFieldsDialogOpened.value) {
            CustomAlertDialog(
                title = "Вы уверены, что хотите очистить все поля?",
                isDialogOpened = clearFieldsDialogOpened,
                onConfirm = {
                    ticket.value = TicketEntity()
                    clearFieldsDialogOpened.value = false
                }
            )
        }
    }
}

@Composable
internal fun TicketUpdateTopBar(
    modifier: Modifier = Modifier,
    ticket: MutableState<TicketEntity>,
    navigateToBack: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .height(65.dp)
            .background(MaterialTheme.colors.onBackground),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .padding(start = 15.dp)
                .fillMaxHeight()
                .width(30.dp)
                .clickable { navigateToBack() },
            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
            contentDescription = "Back",
            tint = MaterialTheme.colors.onPrimary,
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 15.dp),
                text = "Заявка №${ticket.value.id}",
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@Preview(widthDp = 300)
@Composable
private fun Preview() {
    AppTheme(isSystemInDarkTheme()) {
        TicketCreateBottomBar()
    }
}