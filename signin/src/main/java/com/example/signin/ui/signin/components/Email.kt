package com.example.signin.ui.signin.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.R

internal class Email {
    companion object {
        @Composable
        fun Content(
            modifier: Modifier = Modifier,
            email: MutableState<String> = remember { mutableStateOf("") }
        ) {
            OutlinedTextField(
                value = email.value,
                modifier = modifier,
                shape = RoundedCornerShape(15.dp),
                maxLines = 1,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.onBackground,
                    unfocusedBorderColor = MaterialTheme.colors.onBackground,
                    textColor = MaterialTheme.colors.onBackground,
                    focusedLabelColor = MaterialTheme.colors.onBackground,
                    unfocusedLabelColor = MaterialTheme.colors.onBackground,
                    cursorColor = MaterialTheme.colors.onBackground,
                ),
                label = {
                    Text(
                        text = stringResource(R.string.email),
                        fontSize = MaterialTheme.typography.h3.fontSize
                    )
                },
                onValueChange = { email.value = it }
            )
        }

    }
}