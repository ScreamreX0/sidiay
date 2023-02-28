package com.example.home.ui.tickets_list.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.core.ui.theme.AppTheme
import com.example.home.ui.tickets_list.TicketsListViewModel
import com.example.home.ui.tickets_list.ui.components.TicketsListItem


class TicketsList {
    companion object {
        @Composable
        fun Screen(
            navController: NavHostController,
            ticketsListViewModel: TicketsListViewModel = hiltViewModel()
        ) {
            val isDarkTheme = remember { false }

            ticketsListViewModel.testGettingsArgs()

            AppTheme {
                Content(
                    navController = navController,
                    isDarkTheme = isDarkTheme,
                )
            }
        }

        @Composable
        private fun Content(
            navController: NavHostController,
            isDarkTheme: Boolean
        ) {
            Surface(
                Modifier
                    .fillMaxSize()
            ) {
                LazyColumn {
                    items(
                        count = 10,
                        key = {
                            it
                        },
                        itemContent = {
                            TicketsListItem.Content(
                                isDarkTheme = isDarkTheme
                            )
                        }
                    )
                    //navController.navigate(Graphs.HOME)
                }
            }
        }
    }

    @Preview(showBackground = true, device = Devices.PIXEL_2)
    @Composable
    private fun TicketsListPreview() {
        AppTheme {
            Content(
                navController = rememberNavController(),
                isDarkTheme = false,
            )
        }
    }
}