package com.example.home.ui.home.components.top_bar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.core.ui.theme.AppTheme
import com.example.core.ui.utils.ComponentPreview

internal class SearchComponent {
    @Composable
    fun Content(
        textState: MutableState<TextFieldValue> = remember { mutableStateOf(TextFieldValue("")) },
        isSearchEnabled: MutableState<Boolean> = remember { mutableStateOf(true) },
    ) {
        TextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    Icons.Default.ArrowBack,
                    modifier = Modifier
                        .padding(15.dp)
                        .size(24.dp)
                        .clickable {
                            isSearchEnabled.value = false
                        },
                    contentDescription = null,
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = { textState.value = TextFieldValue("") }
                ) {
                    Icon(
                        Icons.Default.Close,
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp),
                        tint = MaterialTheme.colors.onBackground,
                        contentDescription = null,
                    )
                }
            },
            singleLine = true,
            shape = RectangleShape,
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colors.onBackground,
                cursorColor = MaterialTheme.colors.onBackground,
                leadingIconColor = MaterialTheme.colors.onBackground,
                trailingIconColor = MaterialTheme.colors.onBackground,
                backgroundColor = MaterialTheme.colors.background,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
        )
    }

    @ComponentPreview
    @Composable
    private fun Preview() {
        AppTheme(isSystemInDarkTheme()) {
            val textState = remember { mutableStateOf(TextFieldValue("")) }
            Content(textState)
        }
    }
}