package com.example.signin.ui.sign_in.dialog_connections

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core.ui.theme.AppTheme
import com.example.core.ui.theme.DefaultTextStyle
import com.example.core.ui.utils.ComponentPreview
import com.example.core.ui.utils.ScreenPreview


class ConnectionsDialog {
    @Composable
    internal fun ConnectionsDialogScreen(
        isDarkTheme: MutableState<Boolean> = remember { mutableStateOf(false) }
    ) {
        val openDialog = remember { mutableStateOf(true) }

        if (!openDialog.value) {
            return
        }

        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            text = {
                Column {
                    TitleComponent()

                    NewConnectionComponent()

                    Spacer(modifier = Modifier)

                    ConnectionsListComponent()

                    DialogBottomButtonsComponent(openDialog = openDialog)
                }
            },
            buttons = {

            }
        )
    }

    @Composable
    private fun TitleComponent() {
        DefaultTextStyle {
            Text(
                text = "Connections",
                modifier = Modifier
                    .padding(bottom = 10.dp),
                fontSize = MaterialTheme.typography.h3.fontSize,
                fontWeight = FontWeight.Bold,
            )
        }
    }

    @Composable
    private fun NewConnectionComponent() {
        Column {
            TextField(value = "", onValueChange = {})

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp)
            ) {
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Clear all")
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp),
                    onClick = { /*TODO*/ }) {
                    Text(text = "Add")
                }
            }
        }
    }

    @Composable
    private fun ConnectionsListComponent() {
        LazyColumn {
            items(
                items = listOf("Con1", "Con2", "Con3")
            ) { connectionName ->
                Text(text = connectionName)
            }
        }
    }

    @Composable
    private fun DialogBottomButtonsComponent(
        openDialog: MutableState<Boolean> = mutableStateOf(true)
    ) {
        Row(
            modifier = Modifier
                .padding(top = 10.dp)
        ) {
            Button(
                onClick = { openDialog.value = false },
            ) {
                Text("Save")
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                onClick = { openDialog.value = false }
            ) {
                Text("Cancel")
            }
        }
    }

    //
    // Preview
    //
    @ScreenPreview
    @Composable
    private fun ScreenPreview() {
        AppTheme(isSystemInDarkTheme()) {
            ConnectionsDialogScreen()
        }
    }

    @ComponentPreview
    @Composable
    private fun ComponentsPreview() {
        AppTheme {
            //TitleComponent()
            //NewConnectionComponent()
            //ConnectionsListComponent()
        }
    }
}

