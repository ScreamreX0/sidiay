package com.example.home.ui.ticket_create.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
internal fun CustomTextField(
    text: MutableState<String?>,
    hint: String
) {
    BasicTextField(
        value = text.value ?: "",
        onValueChange = { text.value = it },
        textStyle = TextStyle.Default.copy(
            fontSize = 24.sp,
            color = MaterialTheme.colors.onBackground,
        ),
        cursorBrush = SolidColor(MaterialTheme.colors.onBackground),
        decorationBox = { innerTextField ->
            if (text.value?.isEmpty() != false) {
                Text(
                    fontSize = 24.sp,
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.7F),
                    text = text.value ?: hint,
                )
            }
            innerTextField()
        }
    )
}

@Composable
internal fun CustomSelectableText(
    modifier: Modifier = Modifier,
    label: String?,
    nullLabel: String,
    onClick: () -> Unit = {},
) {
    if (label == null) {
        Text(
            modifier = modifier
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) { onClick() },
            style = TextStyle(textDecoration = TextDecoration.Underline),
            text = nullLabel,
            fontSize = MaterialTheme.typography.h5.fontSize,
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.8F),
        )
    } else {
        Text(
            modifier = modifier
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) { onClick() },
            text = label,
            fontSize = MaterialTheme.typography.h5.fontSize,
            color = MaterialTheme.colors.onBackground,
        )
    }
}

@Composable
internal fun CustomTitle(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit,
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp, start = 25.dp, end = 25.dp),
        text = text,
        fontSize = fontSize,
        overflow = TextOverflow.Visible,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.onBackground.copy(alpha = 0.9F)
    )
}