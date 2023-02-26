package com.example.signin.presentation.ui.sign_in.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.R

internal class Title {
    companion object {
        @Composable
        fun Content(
            modifier: Modifier = Modifier,
            isDarkTheme: MutableState<Boolean> = remember { mutableStateOf(false) }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .size(
                        width = 250.dp,
                        height = 65.dp
                    ),
            ) {
                HeaderFirstPart()
                HeaderSecondPart()
                Icon(isDarkTheme = isDarkTheme)
            }
        }

        @Composable
        private fun HeaderFirstPart() {
            Text(
                modifier = Modifier
                    .padding(end = 5.dp),
                letterSpacing = 2.sp,
                color = MaterialTheme.colors.secondary,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                text = stringResource(id = R.string.auth_title_1),
            )
        }

        @Composable
        private fun HeaderSecondPart() {
            Text(
                modifier = Modifier
                    .padding(end = 5.dp),
                fontSize = 40.sp,
                letterSpacing = 2.sp,
                color = MaterialTheme.colors.onBackground,
                fontWeight = FontWeight.Bold,
                text = stringResource(id = R.string.auth_title_2),
            )
        }

        @Composable
        private fun Icon(isDarkTheme: MutableState<Boolean>) {
            val interactionSource = remember { MutableInteractionSource() }
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        isDarkTheme.value = !isDarkTheme.value
                    },
                contentScale = ContentScale.Inside,
                painter = painterResource(id = R.drawable.ic_logo),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.secondary),
                contentDescription = ""
            )
        }
    }
}