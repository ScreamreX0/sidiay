package com.example.home.ui

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.example.core.navigation.Graphs


@Composable
fun TicketsListScreen(navController: NavHostController) {
    Text(
        modifier = Modifier
            .clickable {
                navController.navigate(Graphs.HOME)
            },
        text = "Tickets list",
        fontWeight = FontWeight.Bold
    )
}