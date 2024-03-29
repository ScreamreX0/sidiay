package com.example.home.ui.common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp


@Composable
internal fun CustomTextField(
    text: String?,
    onValueChange: (newValue: String) -> Unit,
    hint: String,
    isClickable: Boolean,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
) {
    BasicTextField(
        enabled = isClickable,
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
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.9F),
                    text = text ?: hint,
                )
            }
            innerTextField()
        },
        keyboardOptions = keyboardOptions
    )
}

@Composable
internal fun CustomText(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit = {},
) {
    Text(
        modifier = modifier
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null
            ) { onClick() },
        text = label,
        fontSize = MaterialTheme.typography.h5.fontSize,
        color = MaterialTheme.colors.onBackground.copy(alpha = 0.9F),
    )
}