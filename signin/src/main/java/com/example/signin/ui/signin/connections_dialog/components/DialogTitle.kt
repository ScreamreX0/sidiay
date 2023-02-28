package com.example.signin.ui.signin.connections_dialog.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core.R
import com.example.core.ui.theme.DefaultTextStyle

internal class DialogTitle {
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
