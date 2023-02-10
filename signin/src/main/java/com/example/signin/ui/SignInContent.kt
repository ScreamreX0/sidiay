package com.example.signin.ui

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout


@Preview
@Composable
fun SignIn() {
    ConstraintLayout {
        SignInButton()
    }
}

@Composable
private fun SignInButton() {
    Button(onClick = { /*TODO*/ }) {

    }
}