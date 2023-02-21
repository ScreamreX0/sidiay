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
    onPrimary = Color.White,

    secondary = Color.Black,
    secondaryVariant = Color.White,

    error = CustomColors.DarkRed700,
    onError = Color.White,

    background = Color.White,
    onBackground = CustomColors.Orange700,

    surface = Color.White,
)

@SuppressLint("ConflictingOnColor")
private val DarkThemeColors = darkColors(
    primary = CustomColors.Grey700,
    primaryVariant = CustomColors.Grey780,
    onPrimary = Color.White,

    secondary = Color.White,
    secondaryVariant = Color.Black,

    error = CustomColors.LightRed700,
    onError = Color.White,

    background = CustomColors.Grey700,
    onBackground = CustomColors.Orange700,

    surface = CustomColors.Grey620,
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
        shapes = MaterialTheme.shapes.copy(small = RoundedCornerShape(15.dp)),
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

@Composable
fun DefaultTextStyle(content: @Composable () -> Unit) {
    MaterialTheme(
        typography = MaterialTheme.typography.copy(
            h1 = MaterialTheme.typography.h1,
            h2 = MaterialTheme.typography.h2,
            h3 = MaterialTheme.typography.h3,
            h4 = MaterialTheme.typography.h4,
            h5 = MaterialTheme.typography.h5,
            h6 = MaterialTheme.typography.h6,
        ),
        colors = MaterialTheme.colors.copy(
            primary = MaterialTheme.colors.primary,
            onPrimary = MaterialTheme.colors.onPrimary
        )
    ) {
        content()
    }
}