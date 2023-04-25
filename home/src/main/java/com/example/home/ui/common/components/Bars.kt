package com.example.home.ui.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.core.R
import com.example.core.navigation.BottomBarNav
import com.example.core.ui.components.CustomAlertDialog
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.AuthParams
import com.example.domain.data_classes.params.TicketRestriction

@Composable
internal fun TicketCreateBottomBar(
    modifier: Modifier = Modifier,
    draft: MutableState<TicketEntity>,
    saveTicketFunction: (String?, TicketEntity) -> Unit,
    authParams: AuthParams,
    bottomBarSelectable: MutableState<Boolean>
) {
    Row(modifier = modifier.height(50.dp)) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.onBackground)
                .clickable {
                    if (bottomBarSelectable.value) {
                        saveTicketFunction(authParams.connectionParams?.url, draft.value)
                    }
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Сохранить",
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@Composable
internal fun TicketUpdateBottomBar(
    modifier: Modifier = Modifier,
    updateTicket: () -> Unit = { },
    bottomBarSelectable: MutableState<Boolean>
) {
    Row(modifier = modifier.height(50.dp)) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.onBackground)
                .clickable { if (bottomBarSelectable.value) { updateTicket() } },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Сохранить",
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@Composable
internal fun TicketCreateTopBar(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    iconsVisible: MutableState<Boolean> = remember { mutableStateOf(true) },
    clearFieldsDialogOpened: MutableState<Boolean> = remember { mutableStateOf(false) },
    draft: MutableState<TicketEntity>
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
                .clickable {
                    navController.popBackStack(
                        route = BottomBarNav.Home.route,
                        inclusive = false
                    )
                },
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
                        .width(30.dp),
                    painter = painterResource(id = R.drawable.baseline_save_as_24),
                    contentDescription = "Save draft",
                    tint = MaterialTheme.colors.onPrimary,
                )

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
                    draft.value = draft.value.copy(
                        name = null,
                        facilities = null,
                        service = null,
                        kind = null,
                        plane_date = null,
                        priority = null,
                        executor = null
                    )
                    clearFieldsDialogOpened.value = false
                }
            )
        }
    }
}

@Composable
internal fun TicketUpdateTopBar(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    iconsVisible: MutableState<Boolean> = remember { mutableStateOf(true) },
    ticket: MutableState<TicketEntity>
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
                .clickable {
                    navController.popBackStack(
                        route = BottomBarNav.Home.route,
                        inclusive = false
                    )
                },
            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
            contentDescription = "Back",
            tint = MaterialTheme.colors.onPrimary,
        )

        if (iconsVisible.value) {
            Row {

            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    TicketCreateTopBar(draft = remember { mutableStateOf(TicketEntity()) })
}