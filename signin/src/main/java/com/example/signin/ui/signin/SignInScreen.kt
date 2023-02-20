package com.example.signin.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.core.ui.theme.AppTheme
import com.example.signin.ui.signin.*

@Composable
fun SignInScreen(navController: NavHostController) {
    AppTheme {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            val (
                emailTextField,
                passwordTextField,
                enterButton,
                offlineEnterButton,
                rememberMeCheckBox,
                rememberMeText,
                forgotPasswordButton
            ) = createRefs()

            val email = remember { mutableStateOf("") }
            val password = remember { mutableStateOf("") }
            val rememberMe = remember { mutableStateOf(false) }

            EmailTextField(
                email = email,
                modifier = Modifier
                    .constrainAs(emailTextField) {
                        start.linkTo(parent.start, margin = 30.dp)
                        end.linkTo(parent.end, margin = 30.dp)
                        linkTo(parent.top, parent.bottom, bias = 0.3F)
                    }
                    .fillMaxWidth(),
            )

            PasswordTextField(
                password = password,
                modifier = Modifier
                    .constrainAs(passwordTextField) {
                        start.linkTo(parent.start, margin = 30.dp)
                        end.linkTo(parent.end, margin = 30.dp)
                        top.linkTo(emailTextField.bottom)
                    })

            EnterButton(
                navController = navController,
                modifier = Modifier
                    .constrainAs(enterButton) {
                        bottom.linkTo(offlineEnterButton.top, margin = 8.dp)
                        linkTo(
                            offlineEnterButton.start,
                            offlineEnterButton.end,
                            bias = 0F
                        )
                    },
                email = email,
                password = password,
                rememberMe = rememberMe
            )

            RememberMeCheckBox(
                modifier = Modifier
                    .constrainAs(rememberMeCheckBox) {
                        start.linkTo(enterButton.end)
                        linkTo(enterButton.top, enterButton.bottom, bias = 0.5F)
                    },
                rememberMe = rememberMe,
            )

            OfflineModeButton(
                navController = navController,
                modifier = Modifier
                    .constrainAs(offlineEnterButton) {
                        top.linkTo(passwordTextField.bottom, margin = 80.dp)
                        linkTo(
                            passwordTextField.start,
                            passwordTextField.end,
                            bias = 0.5F
                        )
                    },
            )

            ForgotPassword(
                navController = navController,
                modifier = Modifier
                    .constrainAs(forgotPasswordButton) {
                        linkTo(
                            offlineEnterButton.start,
                            offlineEnterButton.end,
                            bias = 0.5F
                        )
                        top.linkTo(offlineEnterButton.bottom, margin = 16.dp)
                    }
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    locale = "ru",
    device = Devices.NEXUS_5
)
@Composable
fun SignInScreenPreview1() {
    SignInScreen(navController = rememberNavController())
}