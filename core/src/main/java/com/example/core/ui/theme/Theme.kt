package com.example.core.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@SuppressLint("ConflictingOnColor")
private val LightThemeColors = lightColors(
    primary = CustomColors.Orange700,
    primaryVariant = CustomColors.Orange780,
    onPrimary = CustomColors.Grey700,

    secondary = CustomColors.Grey700,
    secondaryVariant = CustomColors.Grey780,

    error = CustomColors.DarkRed700,
    onError = CustomColors.Grey900,

    background = Color.White,
    onBackground = CustomColors.Orange620,

    surface = Color.White,
)

private val DarkThemeColors = darkColors(
    primary = CustomColors.Grey700,
    primaryVariant = CustomColors.Grey780,
    onPrimary = CustomColors.Orange700,

    secondary = CustomColors.Orange700,
    secondaryVariant = CustomColors.Orange780,

    error = CustomColors.LightRed700,
    onError = CustomColors.Grey500,

    background = CustomColors.Grey500,
    onBackground = CustomColors.Grey620,

    surface = CustomColors.Grey540,
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) {
        DarkThemeColors
    } else {
        LightThemeColors
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        content = content
    )
}

@Composable
fun DefaultButtonStyle(content: @Composable () -> Unit) {
    MaterialTheme(
        shapes = MaterialTheme.shapes.copy(small = RoundedCornerShape(8.dp)),
        typography = MaterialTheme.typography.copy(
            button = MaterialTheme.typography.button.merge(
                TextStyle(
                    fontSize = MaterialTheme.typography.button.fontSize,
                    fontWeight = MaterialTheme.typography.button.fontWeight,
                )
            )
        ),
        colors = MaterialTheme.colors.copy(
            primary = MaterialTheme.colors.primary,
            onPrimary = MaterialTheme.colors.onPrimary
        )
    ) {
        content()
    }
}