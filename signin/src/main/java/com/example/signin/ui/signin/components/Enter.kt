package com.example.signin.ui.signin.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.core.R
import com.example.core.navigation.Graphs
import com.example.core.ui.theme.DefaultButtonStyle
import com.example.domain.models.params.ConnectionParams

internal class Enter {
    companion object {
        @Composable
        fun Content(
            modifier: Modifier = Modifier,
            email: MutableState<String>,
            password: MutableState<String>,
            rememberMe: MutableState<Boolean> = remember { mutableStateOf(false) },
            selectedConnection: MutableState<ConnectionParams>,
            signInFunction: (String, String, String) -> Unit
        ) {
            DefaultButtonStyle {
                Button(
                    modifier = modifier,
                    onClick = {
                        signInFunction(
                            selectedConnection.value.url,
                            email.value,
                            password.value,
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.onBackground,
                    )
                ) {
                    Text(
                        text = stringResource(R.string.sign_in),
                        fontSize = MaterialTheme.typography.h3.fontSize,
                        color = MaterialTheme.colors.onPrimary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 10.dp)
                    )
                }
            }
        }
    }
}