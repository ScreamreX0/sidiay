package com.example.signin.ui.signin.connections_dialog.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.R
import com.example.core.ui.theme.DefaultButtonStyle
import com.example.core.ui.theme.DefaultTextStyle
import com.example.domain.data_classes.params.ConnectionParams

internal class AddConnection {
    companion object {
        @Composable
        fun Content(
            connectionsList: MutableState<List<ConnectionParams>>
        ) {
            Column {
                val context = LocalContext.current
                val connectionName = remember { mutableStateOf("") }
                val url = remember { mutableStateOf("") }

                ConnectionNameTextField(
                    connectionName = connectionName
                )

                /** Connection url text field */
                ConnectionURLTextField(
                    url = url
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp)
                ) {
                    /** Clear all connections button */
                    ClearAllConnectionsButton(
                        connectionsList = connectionsList
                    )

                    /** Add connection button */
                    AddConnectionButton(
                        connectionName = connectionName,
                        context = context,
                        connectionsList = connectionsList,
                        url = url
                    )
                }
            }
        }

        @Composable
        private fun ConnectionNameTextField(connectionName: MutableState<String>) {
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
        }

        @Composable
        private fun ConnectionURLTextField(url: MutableState<String>) {
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
        }

        @Composable
        private fun ClearAllConnectionsButton(connectionsList: MutableState<List<ConnectionParams>>) {
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
        }

        @Composable
        private fun AddConnectionButton(
            connectionName: MutableState<String>,
            context: Context,
            connectionsList: MutableState<List<ConnectionParams>>,
            url: MutableState<String>
        ) {
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