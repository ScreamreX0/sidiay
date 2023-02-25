package com.example.signin.presentation.ui.sign_in

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.core.R
import com.example.core.ui.theme.AppTheme
import com.example.core.ui.theme.DefaultButtonStyle
import com.example.core.ui.theme.DefaultTextStyle
import com.example.core.ui.utils.ComponentPreview
import com.example.core.ui.utils.ScreenPreview
import com.example.core.ui.utils.Variables
import com.example.domain.models.params.ConnectionParams
import kotlinx.coroutines.launch


class ConnectionsDialog {
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
                val tempConnectionsList = remember { connectionsList }

                DialogTitleComponent.TitleContent()

                AddConnectionComponent.Content(
                    connectionsList = tempConnectionsList
                )

                DefaultConnectionComponent.Content(
                    selectedConnection = selectedConnection,
                    isDialogOpened = isDialogOpened
                )

                ConnectionsListComponent.Content(
                    connectionsList = tempConnectionsList,
                    selectedConnection = selectedConnection,
                    isDialogOpened = isDialogOpened
                )

                BottomButtonsComponent.Content(
                    saveDataFunction = saveDataFunction,
                    connectionsList = tempConnectionsList,
                    isDialogOpened = isDialogOpened,
                )
            }
        })
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

private class DialogTitleComponent {
    companion object {
        @Composable
        fun TitleContent() {
            DefaultTextStyle {
                Text(
                    text = stringResource(id = R.string.connections),
                    modifier = Modifier.padding(bottom = 10.dp),
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            }
        }
    }
}

private class AddConnectionComponent {
    companion object {
        @Composable
        fun Content(
            connectionsList: MutableState<List<ConnectionParams>>
        ) {
            Column {
                val context = LocalContext.current
                val connectionName = remember { mutableStateOf("") }
                val url = remember { mutableStateOf("") }

                /** Connection name text field */
                TextField(
                    modifier = Modifier.padding(bottom = 10.dp),
                    value = connectionName.value,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.onBackground,
                        unfocusedBorderColor = MaterialTheme.colors.onBackground,
                        textColor = MaterialTheme.colors.onBackground,
                        focusedLabelColor = MaterialTheme.colors.onBackground,
                        unfocusedLabelColor = MaterialTheme.colors.onBackground,
                        cursorColor = MaterialTheme.colors.onBackground,
                    ),
                    onValueChange = { connectionName.value = it },
                    label = { Text(text = stringResource(R.string.connection_name)) },
                )

                /** Connection url text field */
                TextField(
                    modifier = Modifier.padding(bottom = 5.dp),
                    value = url.value,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.onBackground,
                        unfocusedBorderColor = MaterialTheme.colors.onBackground,
                        textColor = MaterialTheme.colors.onBackground,
                        focusedLabelColor = MaterialTheme.colors.onBackground,
                        unfocusedLabelColor = MaterialTheme.colors.onBackground,
                        cursorColor = MaterialTheme.colors.onBackground,
                    ),
                    onValueChange = { url.value = it },
                    label = { Text(text = stringResource(R.string.url)) },
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp)
                ) {
                    /** Clear all connections button */
                    DefaultButtonStyle {
                        Button(modifier = Modifier.height(40.dp), colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.onBackground,
                            contentColor = MaterialTheme.colors.onPrimary
                        ), onClick = {
                            connectionsList.value = listOf()
                        }) {
                            DefaultTextStyle {
                                Text(
                                    text = stringResource(R.string.clear_all),
                                    fontSize = MaterialTheme.typography.h3.fontSize
                                )
                            }
                        }
                    }

                    /** Add connection button */
                    DefaultButtonStyle {
                        val fillAllFields = stringResource(R.string.fill_all_fields)
                        val tooManyConnections = stringResource(R.string.too_many_connections)
                        val connectionAdded = stringResource(R.string.connection_added)
                        Button(modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .padding(start = 10.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.onBackground,
                                contentColor = MaterialTheme.colors.onPrimary
                            ),
                            onClick = {
                                if (connectionName.value.isBlank() || url.value.isBlank()) {
                                    Toast.makeText(
                                        context, fillAllFields, Toast.LENGTH_SHORT
                                    ).show()
                                    return@Button
                                }

                                if (connectionsList.value.size > 10) {
                                    Toast.makeText(
                                        context, tooManyConnections, Toast.LENGTH_SHORT
                                    ).show()
                                    return@Button
                                }

                                connectionsList.value = connectionsList.value
                                    .plus(
                                        ConnectionParams(connectionName.value, url.value)
                                    )
                                connectionName.value = ""
                                url.value = ""

                                Toast.makeText(
                                    context, connectionAdded, Toast.LENGTH_SHORT
                                ).show()
                            }) {
                            DefaultTextStyle {
                                Text(
                                    text = stringResource(R.string.add),
                                    fontSize = MaterialTheme.typography.h3.fontSize
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private class DefaultConnectionComponent {
    companion object {
        @Composable
        fun Content(
            selectedConnection: MutableState<ConnectionParams>, isDialogOpened: MutableState<Boolean>
        ) {
            val defaultConnectionName = stringResource(id = R.string.default_connection)

            Row(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .clickable(onClick = {
                        selectedConnection.value = ConnectionParams(
                            name = defaultConnectionName, url = Variables.DEFAULT_CONNECTION_URL
                        )
                        isDialogOpened.value = false
                    }), verticalAlignment = Alignment.CenterVertically
            ) {
                DefaultTextStyle {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.8F),
                        text = defaultConnectionName,
                        fontSize = MaterialTheme.typography.h3.fontSize,
                        color = MaterialTheme.colors.onBackground
                    )
                }
                val context = LocalContext.current
                IconButton(onClick = {
                    Toast.makeText(
                        context, "URL: ${Variables.DEFAULT_CONNECTION_URL}", Toast.LENGTH_SHORT
                    ).show()
                }) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_help_outline_24),
                        contentDescription = null,
                        tint = MaterialTheme.colors.onBackground
                    )
                }
            }
        }
    }
}

private class ConnectionsListComponent {
    companion object {
        @Composable
        fun Content(
            connectionsList: MutableState<List<ConnectionParams>>,
            selectedConnection: MutableState<ConnectionParams>,
            isDialogOpened: MutableState<Boolean>
        ) {
            LazyColumn(
                modifier = Modifier.height(150.dp),
            ) {
                items(
                    items = connectionsList.value
                ) { connectionItem ->
                    Row(
                        modifier = Modifier.clickable {
                            selectedConnection.value = connectionItem
                            isDialogOpened.value = false
                        }, verticalAlignment = Alignment.CenterVertically
                    ) {
                        DefaultTextStyle {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(0.7F),
                                text = connectionItem.name,
                                fontSize = MaterialTheme.typography.h3.fontSize,
                                color = MaterialTheme.colors.onBackground
                            )
                        }
                        IconButton(onClick = {
                            connectionsList.value = connectionsList.value
                                .filter { it != connectionItem }
                                .toMutableList()
                        }) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_delete_24),
                                contentDescription = null,
                                tint = MaterialTheme.colors.onBackground
                            )
                        }
                        val context = LocalContext.current
                        IconButton(onClick = {
                            Toast.makeText(
                                context, "URL: ${connectionItem.url}", Toast.LENGTH_SHORT
                            ).show()
                        }) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_help_outline_24),
                                contentDescription = null,
                                tint = MaterialTheme.colors.onBackground
                            )
                        }
                    }
                }
            }
        }
    }
}

private class BottomButtonsComponent {
    companion object {
        @Composable
        fun Content(
            isDialogOpened: MutableState<Boolean>,
            connectionsList: MutableState<List<ConnectionParams>>,
            saveDataFunction: suspend (connectionsList: List<ConnectionParams>) -> Unit,
        ) {
            val scope = rememberCoroutineScope()
            Row {
                DefaultButtonStyle {
                    Button(
                        modifier = Modifier
                            .weight(0.6F)
                            .padding(end = 10.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.onBackground,
                            contentColor = MaterialTheme.colors.onPrimary
                        ),
                        onClick = {
                            scope.launch {
                                saveDataFunction.invoke(connectionsList.value)
                                isDialogOpened.value = false
                            }
                        },
                    ) {
                        Text(
                            text = stringResource(R.string.save),
                            fontSize = MaterialTheme.typography.h4.fontSize
                        )
                    }
                }

                DefaultButtonStyle {
                    Button(modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5F),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.onBackground,
                            contentColor = MaterialTheme.colors.onPrimary
                        ),
                        onClick = {
                            isDialogOpened.value = false
                        }) {
                        Text(
                            text = stringResource(R.string.cancel),
                            fontSize = MaterialTheme.typography.h4.fontSize
                        )
                    }
                }
            }
        }
    }
}