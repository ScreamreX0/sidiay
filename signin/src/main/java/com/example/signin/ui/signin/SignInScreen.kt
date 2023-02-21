package com.example.signin.ui.signin

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.core.ui.theme.AppTheme

@Composable
fun SignInScreen(navController: NavHostController) {
    val isDarkTheme = remember { mutableStateOf(false) }

    AppTheme(isDarkTheme.value) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            val (
                titleComponentRefs,
                emailComponentRef,
                passwordComponentRef,
                enterComponentRef,
                offlineComponentRef,
                rememberComponentRef,
                forgotComponentRef,
            ) = createRefs()

            val email = remember { mutableStateOf("") }
            val password = remember { mutableStateOf("") }
            val rememberMe = remember { mutableStateOf(false) }

            TitleComponent(
                modifier = Modifier
                    .constrainAs(titleComponentRefs) {
                        linkTo(parent.start, parent.end, bias = 0.5F)
                        linkTo(parent.top, parent.bottom, bias = 0.15F)
                    },
                isDarkTheme = isDarkTheme
            )

            EmailTextFieldComponent(
                modifier = Modifier
                    .constrainAs(emailComponentRef) {
                        top.linkTo(titleComponentRefs.bottom, margin = 90.dp)
                    },
                email = email
            )

            PasswordTextFieldComponent(
                password = password,
                modifier = Modifier
                    .constrainAs(passwordComponentRef) {
                        top.linkTo(emailComponentRef.bottom, margin = 10.dp)
                    },
            )

            EnterButtonComponent(
                navController = navController,
                modifier = Modifier
                    .constrainAs(enterComponentRef) {
                        top.linkTo(passwordComponentRef.bottom, margin = 80.dp)
                    },
                email = email,
                password = password,
                rememberMe = rememberMe
            )

            RememberComponent(
                modifier = Modifier
                    .constrainAs(rememberComponentRef) {
                        bottom.linkTo(enterComponentRef.top)
                        start.linkTo(parent.start, margin = 50.dp)
                    },
                rememberMe = rememberMe,
            )

            ForgotPasswordComponent(
                navController = navController,
                modifier = Modifier
                    .constrainAs(forgotComponentRef) {
                        end.linkTo(enterComponentRef.end, 55.dp)
                        top.linkTo(enterComponentRef.bottom, 10.dp)
                    }
            )

            OfflineModeComponent(
                navController = navController,
                modifier = Modifier
                    .constrainAs(offlineComponentRef) {
                        linkTo(forgotComponentRef.bottom, parent.bottom, bias = 0.7F)
                        linkTo(parent.start, parent.end, bias = 0.5F)
                    },
            )
        }
    }
}

@Preview(showBackground = true, locale = "ru", device = Devices.PIXEL_2, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SignInScreenPreview1() { SignInScreen(navController = rememberNavController()) }