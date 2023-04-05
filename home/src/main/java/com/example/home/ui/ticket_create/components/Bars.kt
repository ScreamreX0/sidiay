package com.example.home.ui.ticket_create.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.core.R
import com.example.core.navigation.BottomBarNav

@Composable
internal fun CustomBottomBar(
    modifier: Modifier = Modifier
) {
    /** Bottom app bar */
    Row(modifier = modifier.height(50.dp)) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.5F)
                .background(MaterialTheme.colors.onBackground),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Сохранить",
                color = MaterialTheme.colors.onPrimary
            )
        }

        // Divider
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .background(MaterialTheme.colors.background)
        ) {}

        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.5F)
                .background(MaterialTheme.colors.onBackground),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Отмена",
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@Composable
internal fun CustomTopBar(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    iconsVisible: MutableState<Boolean> = remember { mutableStateOf(true) }
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
                .width(45.dp)
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
                        .width(30.dp),
                    painter = painterResource(id = R.drawable.baseline_format_clear_24),
                    contentDescription = "Clear all fields",
                    tint = MaterialTheme.colors.onPrimary,
                )
            }
        }
    }
}