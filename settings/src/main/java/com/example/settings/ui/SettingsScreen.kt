package com.example.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core.ui.theme.AppTheme
import com.example.core.utils.ScreenPreview
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.AuthParams
import com.example.domain.data_classes.params.ConnectionParams
import com.example.domain.enums.JobTitlesEnum

class SettingsScreen {
    @Composable
    fun Content(
        authParams: AuthParams = AuthParams(
            user = UserEntity(id = 5),
            connectionParams = ConnectionParams()
        ),
        logout: () -> Unit = {}
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
        ) {

            Text(
                modifier = Modifier,
                text = getUserTitle(authParams),
                color = MaterialTheme.colors.onBackground,
                fontSize = MaterialTheme.typography.h4.fontSize,
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = Modifier,
                text = getUserRole(authParams),
                color = MaterialTheme.colors.onBackground.copy(alpha = 0.8F),
                fontSize = MaterialTheme.typography.h3.fontSize
            )

            Spacer(modifier = Modifier.padding(top = 32.dp, bottom = 32.dp))

            Text(
                modifier = Modifier
                    .clickable { logout() }
                    .fillMaxWidth(),
                text = "Выйти",
                color = MaterialTheme.colors.onBackground,
                fontSize = MaterialTheme.typography.h6.fontSize
            )
        }
    }

    private fun getUserTitle(authParams: AuthParams) = "${authParams.user?.getFullName()}"

    private fun getUserRole(authParams: AuthParams) =
        authParams.user?.employee?.jobTitle?.let { JobTitlesEnum.getByValue(it)?.label } ?: "Пользователь"

    @Composable
    @ScreenPreview
    private fun Preview() {
        AppTheme(isSystemInDarkTheme()) { Content() }
    }
}


