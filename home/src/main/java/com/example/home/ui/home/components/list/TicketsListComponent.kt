package com.example.home.ui.home.components.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.AuthParams

internal class TicketsListComponent {
    @Composable
    fun Content(
        navController: NavHostController = rememberNavController(),
        authParams: MutableState<AuthParams?> = remember { mutableStateOf(AuthParams()) },
        tickets: MutableState<List<TicketEntity>> = remember { mutableStateOf(listOf()) },
        refreshing: MutableState<Boolean> = remember { mutableStateOf(false) },
    ) {
        /** List */
        LazyColumn(
            modifier = Modifier
                .background(MaterialTheme.colors.background.copy(alpha = 0.9F))
                .fillMaxSize(),
            userScrollEnabled = true,
        ) {
            if (!refreshing.value) {
                items(tickets.value.size) {
                    TicketsListItemComponent().Content(
                        navController,
                        isDarkMode = authParams.value?.darkMode ?: false,
                        ticket = tickets.value[it],
                    )
                }
            }
        }
    }
}