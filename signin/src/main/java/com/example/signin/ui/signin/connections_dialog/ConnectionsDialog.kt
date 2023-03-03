package com.example.signin.ui.signin.connections_dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.core.R
import com.example.core.ui.theme.AppTheme
import com.example.core.ui.utils.ComponentPreview
import com.example.core.ui.utils.ScreenPreview
import com.example.core.ui.utils.Variables
import com.example.domain.models.params.ConnectionParams
import com.example.signin.ui.signin.connections_dialog.components.AddConnection
import com.example.signin.ui.signin.connections_dialog.components.BottomButtons
import com.example.signin.ui.signin.connections_dialog.components.ConnectionsList
import com.example.signin.ui.signin.connections_dialog.components.DefaultConnection
import com.example.signin.ui.signin.connections_dialog.components.DialogTitle

internal class ConnectionsDialog {
    companion object {
        @Composable
        internal fun ConnectionsDialogScreen(
            isDialogOpened: MutableState<Boolean> = remember { mutableStateOf(true) },
            selectedConnection: MutableState<ConnectionParams>,
            connectionsList: MutableState<List<ConnectionParams>> = remember { mutableStateOf(listOf()) },
            saveDataFunction: suspend (connectionsList: List<ConnectionParams>) -> Unit = {},
        ) {
            if (!isDialogOpened.value) {
                return
            }

            Dialog(onDismissRequest = { isDialogOpened.value = false }, content = {
                Column(
                    Modifier
                        .background(MaterialTheme.colors.background)
                        .padding(20.dp),
                ) {
                    DialogTitle.TitleContent()

                    AddConnection.Content(
                        connectionsList = connectionsList
                    )

                    DefaultConnection.Content(
                        selectedConnection = selectedConnection,
                        isDialogOpened = isDialogOpened
                    )

                    ConnectionsList.Content(
                        connectionsList = connectionsList,
                        selectedConnection = selectedConnection,
                        isDialogOpened = isDialogOpened
                    )

                    BottomButtons.Content(
                        saveDataFunction = saveDataFunction,
                        connectionsList = connectionsList,
                        isDialogOpened = isDialogOpened,
                    )
                }
            })
        }
    }

    /** Previews */
    @ScreenPreview
    @Composable
    private fun ScreenPreview() {
        AppTheme(isSystemInDarkTheme()) {
            val name = stringResource(id = R.string.default_connection)
            val defaultConnection = remember {
                mutableStateOf(ConnectionParams(name, Variables.DEFAULT_CONNECTION_URL))
            }

            ConnectionsDialogScreen(
                selectedConnection = defaultConnection,
            )
        }
    }

    @ComponentPreview
    @Composable
    private fun ComponentsPreview() {
        AppTheme {
            // Components
        }
    }
}