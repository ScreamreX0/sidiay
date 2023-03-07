package com.example.signin.ui.signin.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.core.R

internal class AutoAuthComponent {
    companion object {
        @Composable
        fun Content(
            modifier: Modifier = Modifier,
            autoAuth: MutableState<Boolean> = remember { mutableStateOf(false) }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
            ) {
                Checkbox(
                    checked = autoAuth.value,
                    onCheckedChange = {
                        autoAuth.value = it
                    },

                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colors.onBackground,
                        uncheckedColor = MaterialTheme.colors.onBackground,
                        checkmarkColor = MaterialTheme.colors.background,
                    )
                )
                Text(
                    text = stringResource(id = R.string.auto_auth),
                    fontSize = MaterialTheme.typography.h2.fontSize,
                    color = MaterialTheme.colors.onBackground
                )
            }

        }
    }
}