package com.example.signin.ui.components

import android.content.Context
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.core.R
import com.example.core.ui.theme.AppTheme
import com.example.core.ui.theme.DefaultButtonStyle
import com.example.core.ui.theme.DefaultTextStyle
import com.example.core.utils.Constants
import com.example.core.utils.Helper
import com.example.core.utils.ScreenPreview
import com.example.domain.data_classes.params.ConnectionParams
import kotlinx.coroutines.launch
import javax.xml.validation.Validator

@Composable
internal fun ConnectionsDialog(
    isDialogOpened: MutableState<Boolean> = mutableStateOf(true),
    selectedConnection: MutableState<ConnectionParams>,
    connectionsList: MutableState<List<ConnectionParams>> = mutableStateOf(listOf()),
    saveConnectionsFunction: suspend (connectionsList: List<ConnectionParams>) -> Unit = {},
    updateConnectionsList: suspend () -> Unit = {}
) {
    if (!isDialogOpened.value) return

    val scope = rememberCoroutineScope()
    LaunchedEffect(true) {
        scope.launch {
            updateConnectionsList.invoke()
        }
    }

    Dialog(onDismissRequest = { isDialogOpened.value = false }, content = {
        Column(
            Modifier
                .background(MaterialTheme.colors.background)
                .padding(20.dp),
        ) {
            // Dialog title
            DefaultTextStyle {
                Text(
                    text = "Соединения",
                    modifier = Modifier.padding(bottom = 10.dp),
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            }

            AddConnectionComponent(
                onClearConnectionsList = { connectionsList.value = listOf() },
                onAddConnection = { connectionName, context, url ->
                    // Validating new connection
                    if (connectionName.isBlank() || url.isBlank()) {
                        Helper.showShortToast(context, "Заполните все поля")
                        return@AddConnectionComponent
                    }
                    if (connectionsList.value.size > 10) {
                        Helper.showShortToast(context, "Слишком много соединений")
                        return@AddConnectionComponent
                    }
                    // Adding new connection
                    connectionsList.value = connectionsList.value.plus(ConnectionParams(connectionName, url))
                    Helper.showShortToast(context, "Соединение добавлено")
                }
            )

            DefaultConnectionComponent(
                selectedConnection = selectedConnection,
                isDialogOpened = isDialogOpened
            )

            ConnectionsListComponent(
                connectionsList = connectionsList,
                selectedConnection = selectedConnection,
                isDialogOpened = isDialogOpened
            )

            BottomBarComponent(
                saveConnectionsFunction = saveConnectionsFunction,
                connectionsList = connectionsList,
                isDialogOpened = isDialogOpened,
            )
        }
    })
}


@Composable
private fun BottomBarComponent(
    isDialogOpened: MutableState<Boolean>,
    connectionsList: State<List<ConnectionParams>?>,
    saveConnectionsFunction: suspend (connectionsList: List<ConnectionParams>) -> Unit,
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
                        connectionsList.value?.let {
                            saveConnectionsFunction.invoke(it)
                            isDialogOpened.value = false
                        }
                    }
                },
            ) {
                Text(
                    text = "Сохранить",
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
                onClick = { isDialogOpened.value = false }) {
                Text(
                    text = "Отмена",
                    fontSize = MaterialTheme.typography.h4.fontSize
                )
            }
        }
    }
}

@Composable
private fun AddConnectionComponent(
    connectionName: MutableState<String> = remember { mutableStateOf("") },
    url: MutableState<String> = remember { mutableStateOf("") },
    onClearConnectionsList: () -> Unit = {},
    onAddConnection: (connectionName: String, context: Context, url: String) -> Unit = { _, _, _ -> }
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
                Button(
                    modifier = Modifier.height(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.onBackground,
                        contentColor = MaterialTheme.colors.onPrimary
                    ), onClick = { onClearConnectionsList() }) {
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
                    onClick = { onAddConnection(connectionName.value, context, url.value) }) {
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

@Composable
private fun ConnectionsListComponent(
    connectionsList: MutableState<List<ConnectionParams>>,
    selectedConnection: MutableState<ConnectionParams>,
    isDialogOpened: MutableState<Boolean>
) {
    val context = LocalContext.current
    LazyColumn(modifier = Modifier.height(150.dp)) {
        items(items = connectionsList.value) { connectionItem ->
            Row(
                modifier = Modifier.clickable {
                    selectedConnection.value = connectionItem
                    isDialogOpened.value = false
                },
                verticalAlignment = Alignment.CenterVertically
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
                Icon(
                    modifier = Modifier
                        .clickable {
                            connectionsList.value = connectionsList.value
                                .filter { it != connectionItem }
                                .toMutableList()
                        },
                    painter = painterResource(R.drawable.baseline_delete_24),
                    contentDescription = "Удалить соединение",
                    tint = MaterialTheme.colors.onBackground,
                )

                Icon(
                    modifier = Modifier
                        .clickable {
                            Toast.makeText(
                                context,
                                "URL: ${connectionItem.url}",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                    painter = painterResource(R.drawable.baseline_help_outline_24),
                    contentDescription = null,
                    tint = MaterialTheme.colors.onBackground
                )
            }
        }
    }
}

@Composable
private fun DefaultConnectionComponent(
    selectedConnection: MutableState<ConnectionParams>,
    isDialogOpened: MutableState<Boolean>
) {
    Row(
        modifier = Modifier
            .padding(top = 5.dp)
            .clickable(onClick = {
                selectedConnection.value = ConnectionParams(
                    name = "Стандартное соединение",
                    url = Constants.URL
                )
                isDialogOpened.value = false
            }),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DefaultTextStyle {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.8F),
                text = "Стандартное соединение",
                fontSize = MaterialTheme.typography.h3.fontSize,
                color = MaterialTheme.colors.onBackground
            )
        }
        val context = LocalContext.current
        IconButton(onClick = {
            Toast.makeText(
                context,
                "URL: ${Constants.URL}",
                Toast.LENGTH_SHORT
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

@ScreenPreview
@Composable
private fun ScreenPreview() {
    AppTheme(isSystemInDarkTheme()) {
        val name = "Стандартное соединение"
        val defaultConnection = remember {
            mutableStateOf(ConnectionParams(name, Constants.URL))
        }

        ConnectionsDialog(
            selectedConnection = defaultConnection,
        )
    }
}