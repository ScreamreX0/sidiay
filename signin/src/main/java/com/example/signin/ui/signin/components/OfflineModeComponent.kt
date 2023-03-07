package com.example.signin.ui.signin.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.core.R
import com.example.core.navigation.Graphs

internal class OfflineModeComponent {
    companion object {
        @Composable
        fun Content(
            modifier: Modifier = Modifier,
            navController: NavHostController = rememberNavController()
        ) {
            val interactionSource = remember { MutableInteractionSource() }
            Text(
                modifier = modifier
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        navController.popBackStack()
                        navController.navigate(Graphs.MAIN_MENU)
                    },
                text = stringResource(id = R.string.offline_mode),
                color = MaterialTheme.colors.onBackground,
                fontWeight = FontWeight.Bold
            )
        }
    }
}