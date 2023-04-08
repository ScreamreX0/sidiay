package com.example.home.ui.common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.example.domain.data_classes.entities.DraftEntity


@Composable
internal fun CustomTextField(
    text: String?,
    onValueChange: (newValue: String) -> Unit,
    hint: String,
) {
    BasicTextField(
        value = text ?: "",
        onValueChange = { onValueChange(it) },
        textStyle = TextStyle.Default.copy(
            fontSize = 24.sp,
            color = MaterialTheme.colors.onBackground,
        ),
        cursorBrush = SolidColor(MaterialTheme.colors.onBackground),
        decorationBox = { innerTextField ->
            if (text?.isEmpty() != false) {
                Text(
                    fontSize = 24.sp,
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.7F),
                    text = text ?: hint,
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