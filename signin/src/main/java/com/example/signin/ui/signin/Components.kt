package com.example.signin.ui.signin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.core.navigation.Graphs
import com.example.core.ui.theme.DefaultButtonStyle


@Composable
internal fun EmailTextField(modifier: Modifier, email: MutableState<String>) {
    OutlinedTextField(
        value = email.value,
        modifier = modifier
            .padding(start = 32.dp, end = 32.dp)
            .fillMaxWidth(),
        label = { Text(text = stringResource(com.example.core.R.string.email)) },
        onValueChange = { email.value = it }
    )
}

@Composable
internal fun PasswordTextField(modifier: Modifier, password: MutableState<String>) {
    val passwordVisible = remember { mutableStateOf(false) }
    OutlinedTextField(
        value = password.value,
        modifier = modifier
            .padding(start = 32.dp, end = 32.dp)
            .fillMaxWidth(),
        label = { Text(text = stringResource(com.example.core.R.string.password)) },
        visualTransformation = if (passwordVisible.value) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        onValueChange = { password.value = it },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image: Int = if (passwordVisible.value) {
                com.example.core.R.drawable.baseline_visibility_24
            } else {
                com.example.core.R.drawable.baseline_visibility_off_24
            }

            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(painterResource(id = image), "")
            }
        }
    )
}

@Composable
internal fun EnterButton(
    modifier: Modifier,
    navController: NavHostController,
    email: MutableState<String>,
    password: MutableState<String>,
    rememberMe: MutableState<Boolean>
) {
    DefaultButtonStyle {
        Button(
            modifier = modifier,
            onClick = {
                navController.popBackStack()
                navController.navigate(Graphs.MAIN_MENU)
            }
        ) {
            Text(
                text = stringResource(com.example.core.R.string.sign_in),
                fontSize = MaterialTheme.typography.h3.fontSize,
            )
        }
    }
}

@Composable
internal fun OfflineModeButton(modifier: Modifier, navController: NavHostController) {
    DefaultButtonStyle {
        Button(
            modifier = modifier,
            onClick = {
                navController.popBackStack()
                navController.navigate(Graphs.MAIN_MENU)
            }
        ) {
            Text(
                // TODO: fix hardcode
                text = "Offline mode",
                fontSize = MaterialTheme.typography.h3.fontSize,
                modifier = Modifier.padding(start = 32.dp, end = 32.dp)
            )
        }
    }
}

@Composable
internal fun ForgotPasswordButton(modifier: Modifier, navController: NavHostController) {
    DefaultButtonStyle {
        Button(
            modifier = modifier,
            onClick = {
                navController.popBackStack()
                navController.navigate(Graphs.MAIN_MENU)
            }
        ) {
            Text(
                // TODO: fix hardcode
                text = "Forgot password?",
                fontSize = MaterialTheme.typography.h4.fontSize,
                modifier = Modifier,
            )
        }
    }
}

@Composable
internal fun RememberMeCheckBox(
    modifier: Modifier,
    rememberMe: MutableState<Boolean>
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Checkbox(
            checked = false,
            onCheckedChange = { rememberMe.value = it },
        )
        // TODO: fix hardcode
        Text(
            text = "Remember",
            fontSize = MaterialTheme.typography.h2.fontSize
        )
    }

}

@Composable
internal fun ForgotPassword(
    navController: NavHostController,
    modifier: Modifier
) {
    Text(
        modifier = modifier,
        text = "Forgot password?"  // TODO: fix hardcode
    )
}