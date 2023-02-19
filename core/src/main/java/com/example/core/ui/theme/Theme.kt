package com.example.main_menu.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.core.ui.theme.CustomColors

private val LightThemeColors = lightColors(
    primary = CustomColors.Blue600,
    primaryVariant = CustomColors.Blue400,
    onPrimary = CustomColors.Black2,
    secondary = Color.White,
    secondaryVariant = CustomColors.Teal300,
    error = CustomColors.RedErrorDark,
    onError = CustomColors.RedErrorLight,
    background = CustomColors.Grey1,
    onBackground = Color.Black,
    surface = Color.White,
)

private val DarkThemeColors = darkColors(
    primary = CustomColors.Blue700,
    primaryVariant = Color.White,
    onPrimary = Color.White,
    secondary = CustomColors.Black1,
    onSecondary = Color.White,
    error = CustomColors.RedErrorLight,
    background = Color.Black,
    onBackground = Color.White,
    surface = CustomColors.Black1,
    onSurface = Color.White,
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
        content = content
    )
}
