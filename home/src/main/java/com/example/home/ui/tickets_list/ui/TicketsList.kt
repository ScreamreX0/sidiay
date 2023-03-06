package com.example.home.ui.tickets_list.ui

import android.widget.ListView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.core.ui.theme.AppTheme
import com.example.core.ui.utils.ScreenPreview
import com.example.domain.models.entities.TicketEntity
import com.example.home.ui.tickets_list.TicketsListViewModel
import com.example.home.ui.tickets_list.ui.components.TicketsListItem
import kotlin.reflect.KMutableProperty0


class TicketsList {
    @Composable
    fun TicketsListScreen(
        ticketsListViewModel: TicketsListViewModel = hiltViewModel(),
        navController: NavHostController = rememberNavController(),
        isDarkTheme: MutableState<Boolean> = remember { mutableStateOf(false) },
        paddingValues: PaddingValues = PaddingValues()
    ) {
        AppTheme {
            Content(
                modifier = Modifier.padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                ),
                navController = navController,
                isDarkTheme = isDarkTheme.value,
                tickets = ticketsListViewModel::tickets
            )
        }
    }

    @Composable
    private fun Content(
        modifier: Modifier = Modifier,
        navController: NavHostController,
        isDarkTheme: Boolean,
        tickets: KMutableProperty0<MutableState<List<TicketEntity>>>
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            TopAppBar(
                contentColor = MaterialTheme.colors.onBackground,
                backgroundColor = MaterialTheme.colors.background
            ) {

            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                userScrollEnabled = true,
            ) {
                items(tickets.get().value.size) {
                    TicketsListItem.Content(
                        navController,
                        isDarkTheme = isDarkTheme
                    )
                }
            }
        }
    }

    var tickets: MutableState<List<TicketEntity>> = mutableStateOf(listOf())

    @ScreenPreview
    @Composable
    private fun TicketsListPreview() {
        AppTheme {
            Content(
                navController = rememberNavController(),
                isDarkTheme = false,
                tickets = this::tickets
            )
        }
    }
}