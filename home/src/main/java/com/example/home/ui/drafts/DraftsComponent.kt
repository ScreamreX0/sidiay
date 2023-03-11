package com.example.home.ui.drafts

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import com.example.domain.data_classes.entities.DraftEntity
import com.example.domain.data_classes.params.AuthParams


class DraftsComponent {
    @Composable
    fun Content(
        navController: NavHostController,
        authParams: MutableState<AuthParams?>,
        drafts: MutableState<List<DraftEntity>>,
    ) {
        Text(text = "Drafts")
    }
}
