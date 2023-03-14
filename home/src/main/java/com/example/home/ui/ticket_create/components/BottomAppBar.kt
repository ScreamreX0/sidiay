package com.example.home.ui.ticket_create.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core.ui.theme.AppTheme
import com.example.core.ui.utils.ScreenPreview


internal class BottomAppBar {
    @Composable
    fun Content(
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
    @ScreenPreview
    private fun Preview() {
        AppTheme(isSystemInDarkTheme()) { Content() }
    }
}