package com.example.signin.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.core.navigation.Graphs
import com.example.main_menu.ui.AppTheme
import com.example.signin.ui.components.SignInTextField

@Composable
fun SignInScreen(navController: NavController) {
    AppTheme(true) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            val (emailTextField, passwordTextField, enterButton, offlineEnterButton) = createRefs()

            SignInTextField(
                hint = "Email",
                label = "",
                modifier = Modifier.constrainAs(emailTextField) {
                    top.linkTo(parent.top, margin = 16.dp)
                }
            )

            SignInTextField(
                hint = "Password",
                label = "",
                modifier = Modifier.constrainAs(passwordTextField) {
                    top.linkTo(emailTextField.bottom, margin = 30.dp)
                },
                isPassword = true
            )

            Button(
                modifier = Modifier
                    .constrainAs(enterButton) {
                        top.linkTo(passwordTextField.bottom, margin = 30.dp)
                    },
                onClick = {
                    navController.popBackStack()
                    navController.navigate(Graphs.MAIN_MENU)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background),
            ) {
                Text(
                    text = "Enter",
                    color = MaterialTheme.colors.onPrimary
                )
            }

            Button(
                modifier = Modifier.constrainAs(offlineEnterButton) {
                    top.linkTo(enterButton.bottom, margin = 30.dp)
                },
                onClick = {
                    navController.popBackStack()
                    navController.navigate(Graphs.MAIN_MENU)
                }) {
                Text(text = "Offline mode")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    SignInScreen(navController = rememberNavController())
}