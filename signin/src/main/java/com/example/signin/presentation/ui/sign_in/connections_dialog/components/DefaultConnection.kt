package com.example.signin.presentation.ui.sign_in.connections_dialog.components

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.R
import com.example.core.ui.theme.DefaultTextStyle
import com.example.core.ui.utils.Variables
import com.example.domain.models.params.ConnectionParams

internal class DefaultConnection {
    companion object {
        @Composable
        fun Content(
            selectedConnection: MutableState<ConnectionParams>,
            isDialogOpened: MutableState<Boolean>
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