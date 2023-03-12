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
            connectionsList: MutableState<List<ConnectionParams>>,
            connectionName: MutableState<String> = remember { mutableStateOf("") },
            url: MutableState<String> = remember { mutableStateOf("") }
        ) {
            val context = LocalContext.current
            Column {
                // Name
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
                    label = { Text(text = "Название соединения") },
                )

                // Url
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
                    label = { Text(text = "URL") },
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp)
                ) {
                    // Clear all
                    DefaultButtonStyle {
                        Button(modifier = Modifier.height(40.dp), colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.onBackground,
                            contentColor = MaterialTheme.colors.onPrimary
                        ), onClick = {
                            connectionsList.value = listOf()
                        }) {
                            DefaultTextStyle {
                                Text(
                                    text = "Очистить все",
                                    fontSize = MaterialTheme.typography.h3.fontSize
                                )
                            }
                        }
                    }

                    // Add connection
                    DefaultButtonStyle {
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
                                        context, "Заполните все поля", Toast.LENGTH_SHORT
                                    ).show()
                                    return@Button
                                }

                                if (connectionsList.value.size > 10) {
                                    Toast.makeText(
                                        context, "Слишком много соединений", Toast.LENGTH_SHORT
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
                                    context, "Соединение добавлено", Toast.LENGTH_SHORT
                                ).show()
                            }) {
                            DefaultTextStyle {
                                Text(
                                    text = "Добавить",
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