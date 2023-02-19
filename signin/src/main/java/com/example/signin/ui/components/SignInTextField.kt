package com.example.signin.ui.components

import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun SignInTextField(
    modifier: Modifier,
    hint: String,
    label: String,
    isPassword: Boolean = false
) {
    if (isPassword) {
        TextFieldPassword(modifier, hint, label)
    } else {
        TextFieldEmail(modifier, hint, label)
    }
}

@Composable
private fun TextFieldEmail(modifier: Modifier, hint: String, label: String) {
    TextField(
        modifier = modifier,
        value = "",
        label = { Text(label) },
        singleLine = true,
        placeholder = { Text(hint) },
        onValueChange = {},
    )
}

@Composable
private fun TextFieldPassword(modifier: Modifier, hint: String, label: String) {
    val password = remember { mutableStateOf("") }
    val passwordVisible = remember { mutableStateOf(false) }

    TextField(
        modifier = modifier,
        value = "",
        label = { Text(label) },
        singleLine = true,
        placeholder = { Text(hint) },
        onValueChange = {},
        visualTransformation = if (passwordVisible.value) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
    )
}