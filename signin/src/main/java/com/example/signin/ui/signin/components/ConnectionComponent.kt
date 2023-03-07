package com.example.signin.ui.signin.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.domain.models.params.ConnectionParams

internal class ConnectionComponent {
    companion object {
        @Composable
        fun Content(
            modifier: Modifier = Modifier,
            isConnectionDialogOpened: MutableState<Boolean>,
            selectedConnection: MutableState<ConnectionParams>,
        ) {
            OutlinedButton(
                modifier = modifier,
                onClick = { isConnectionDialogOpened.value = true },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.background,
                ),
                border = BorderStroke(1.dp, MaterialTheme.colors.onBackground),
                shape = RoundedCornerShape(25),
            ) {
                ButtonText(selectedConnection = selectedConnection)
            }
        }

        @Composable
        private fun ButtonText(selectedConnection: MutableState<ConnectionParams>) {
            Text(
                text = selectedConnection.value.name,
                fontSize = MaterialTheme.typography.h3.fontSize,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp),
                textAlign = TextAlign.Start
            )
        }
    }
}