package com.example.signin.ui.signin.connections_dialog.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.R
import com.example.core.ui.theme.DefaultButtonStyle
import com.example.domain.models.params.ConnectionParams
import kotlinx.coroutines.launch

internal class BottomButtons {
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