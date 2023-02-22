package com.example.signin.ui.sign_in

import android.app.UiModeManager.MODE_NIGHT_YES
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.atLeastWrapContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.core.R
import com.example.core.navigation.Graphs
import com.example.core.ui.theme.AppTheme
import com.example.core.ui.theme.DefaultButtonStyle
import com.example.core.ui.utils.ComponentPreview
import com.example.core.ui.utils.ScreenPreview

class SignIn {
    @Composable
    fun SignInScreen(
        navController: NavHostController = rememberNavController(),
        darkMode: MutableState<Boolean> = remember { mutableStateOf(false) }
    ) {
        AppTheme {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
            ) {
                val (
                    titleComponentRef,
                    connectionComponentRef,
                    emailComponentRef,
                    passwordComponentRef,
                    enterComponentRef,
                    offlineComponentRef,
                    rememberComponentRef,
                ) = createRefs()

                TitleComponent(
                    modifier = Modifier
                        .constrainAs(titleComponentRef) {
                            linkTo(parent.start, parent.end, bias = 0.5F)
                            linkTo(parent.top, parent.bottom, bias = 0.2F)
                        },
                    isDarkTheme = darkMode
                )

                ConnectionComponent(
                    navController = navController,
                    modifier = Modifier
                        .constrainAs(connectionComponentRef) {
                            top.linkTo(titleComponentRef.bottom, 60.dp)
                            linkTo(parent.start, parent.end, bias = 0.5F)
                            width = Dimension.percent(0.8F)
                        }
                )

                val email = remember { mutableStateOf("") }
                EmailComponent(
                    modifier = Modifier
                        .constrainAs(emailComponentRef) {
                            top.linkTo(connectionComponentRef.bottom, margin = 25.dp)
                            linkTo(connectionComponentRef.start, connectionComponentRef.end)
                            width = Dimension.fillToConstraints
                        },
                    email = email
                )

                val password = remember { mutableStateOf("") }
                PasswordComponent(
                    modifier = Modifier
                        .constrainAs(passwordComponentRef) {
                            top.linkTo(emailComponentRef.bottom, margin = 10.dp)
                            linkTo(emailComponentRef.start, emailComponentRef.end)
                            width = Dimension.fillToConstraints
                        },
                    password = password,
                )

                val autoAuth = remember { mutableStateOf(false) }
                AutoAuthComponent(
                    modifier = Modifier
                        .constrainAs(rememberComponentRef) {
                            bottom.linkTo(enterComponentRef.top)
                            start.linkTo(parent.start, margin = 50.dp)
                        },
                    autoAuth = autoAuth,
                )

                EnterComponent(
                    navController = navController,
                    modifier = Modifier
                        .constrainAs(enterComponentRef) {
                            top.linkTo(passwordComponentRef.bottom, margin = 80.dp)
                            linkTo(passwordComponentRef.start, passwordComponentRef.end)
                            width = Dimension.fillToConstraints
                                .atLeastWrapContent
                        },
                    email = remember { mutableStateOf("") },
                    password = password,
                    rememberMe = autoAuth,
                )

                OfflineModeComponent(
                    navController = navController,
                    modifier = Modifier
                        .constrainAs(offlineComponentRef) {
                            linkTo(enterComponentRef.start, enterComponentRef.end, bias = 0.5F)
                            top.linkTo(enterComponentRef.bottom, margin = 10.dp)
                        },
                )
            }
        }
    }

    @Composable
    private fun TitleComponent(
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
            Text(
                modifier = Modifier
                    .padding(end = 5.dp),
                letterSpacing = 2.sp,
                color = MaterialTheme.colors.secondary,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                text = stringResource(id = R.string.auth_title_1),
            )

            Text(
                modifier = Modifier
                    .padding(end = 5.dp),
                fontSize = 40.sp,
                letterSpacing = 2.sp,
                color = MaterialTheme.colors.onBackground,
                fontWeight = FontWeight.Bold,
                text = stringResource(id = R.string.auth_title_2),
            )

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

    @Composable
    private fun ConnectionComponent(
        modifier: Modifier = Modifier,
        navController: NavHostController = rememberNavController()
    ) {
        OutlinedButton(
            modifier = modifier,
            onClick = {
                navController.popBackStack()
                navController.navigate(Graphs.MAIN_MENU)
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.background,
            ),
            border = BorderStroke(1.dp, MaterialTheme.colors.onBackground),
            shape = RoundedCornerShape(25),
        ) {
            Text(
                text = stringResource(R.string.default_connection),
                fontSize = MaterialTheme.typography.h3.fontSize,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp),
                textAlign = TextAlign.Start
            )
        }
    }

    @Composable
    private fun EmailComponent(
        modifier: Modifier = Modifier,
        email: MutableState<String> = remember { mutableStateOf("") }
    ) {
        OutlinedTextField(
            value = email.value,
            modifier = modifier,
            shape = RoundedCornerShape(15.dp),
            maxLines = 1,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.onBackground,
                unfocusedBorderColor = MaterialTheme.colors.onBackground,
                textColor = MaterialTheme.colors.onBackground,
                focusedLabelColor = MaterialTheme.colors.onBackground,
                unfocusedLabelColor = MaterialTheme.colors.onBackground,
                cursorColor = MaterialTheme.colors.onBackground,
            ),
            label = {
                Text(
                    text = stringResource(R.string.email),
                    fontSize = MaterialTheme.typography.h3.fontSize
                )
            },
            onValueChange = { email.value = it }
        )
    }

    @Composable
    private fun PasswordComponent(
        modifier: Modifier = Modifier,
        password: MutableState<String> = mutableStateOf("")
    ) {
        val passwordVisible = remember { mutableStateOf(false) }
        OutlinedTextField(
            value = password.value,
            modifier = modifier,
            shape = RoundedCornerShape(15.dp),
            label = {
                Text(
                    text = stringResource(R.string.password),
                    fontSize = MaterialTheme.typography.h3.fontSize,
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.onBackground,
                unfocusedBorderColor = MaterialTheme.colors.onBackground,
                textColor = MaterialTheme.colors.onBackground,
                focusedLabelColor = MaterialTheme.colors.onBackground,
                unfocusedLabelColor = MaterialTheme.colors.onBackground,
                cursorColor = MaterialTheme.colors.onBackground,
            ),
            visualTransformation = if (passwordVisible.value) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            onValueChange = { password.value = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image: Int = if (passwordVisible.value) {
                    R.drawable.baseline_visibility_24
                } else {
                    R.drawable.baseline_visibility_off_24
                }

                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                    Icon(
                        painter = painterResource(id = image),
                        contentDescription = null,
                        tint = MaterialTheme.colors.onBackground
                    )
                }
            }
        )
    }

    @Composable
    private fun EnterComponent(
        modifier: Modifier = Modifier,
        navController: NavHostController = rememberNavController(),
        email: MutableState<String> = remember { mutableStateOf("") },
        password: MutableState<String> = remember { mutableStateOf("") },
        rememberMe: MutableState<Boolean> = remember { mutableStateOf(false) }
    ) {
        DefaultButtonStyle {
            Button(
                modifier = modifier,
                onClick = {
                    navController.popBackStack()
                    navController.navigate(Graphs.MAIN_MENU)
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

    @Composable
    private fun AutoAuthComponent(
        modifier: Modifier = Modifier,
        autoAuth: MutableState<Boolean> = remember { mutableStateOf(false) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
        ) {
            Checkbox(
                checked = autoAuth.value,
                onCheckedChange = {
                    autoAuth.value = it
                },

                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colors.onBackground,
                    uncheckedColor = MaterialTheme.colors.onBackground,
                    checkmarkColor = MaterialTheme.colors.background,
                )
            )
            Text(
                text = stringResource(id = R.string.auto_auth),
                fontSize = MaterialTheme.typography.h2.fontSize,
                color = MaterialTheme.colors.onBackground
            )
        }

    }

    @Composable
    private fun OfflineModeComponent(
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

    //
    // PREVIEW
    //
    @ScreenPreview
    @Composable
    private fun ScreenPreview() {
        SignInScreen()
    }

    @ComponentPreview
    @Composable
    private fun ComponentsPreview() {
        AppTheme {
            //PasswordTextField()
            //RememberComponent()
            //EnterButtonComponent()
            //OfflineModeComponent()
            //TitleComponent()
            //TitleComponent()
            ConnectionComponent()
        }
    }
}