package com.example.signin.ui.signin.connections_dialog.components

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.core.R
import com.example.core.ui.theme.DefaultTextStyle
import com.example.domain.models.params.ConnectionParams

internal class ConnectionsList {
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