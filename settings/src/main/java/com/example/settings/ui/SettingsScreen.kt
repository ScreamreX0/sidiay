package com.example.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core.ui.theme.AppTheme
import com.example.core.utils.ScreenPreview
import com.example.domain.data_classes.params.AuthParams

class SettingsScreen {
    @Composable
    fun Content(
        authParams: AuthParams = AuthParams(),
        logout: () -> Unit = {}
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.clickable { logout() },
                text = "Выйти",
                color = MaterialTheme.colors.onBackground,
                fontSize = MaterialTheme.typography.h3.fontSize
            )
        }
    }

    @Composable
    @ScreenPreview
    private fun Preview() {
        AppTheme { Content() }
    }
}


