package com.example.signin.presentation.ui.sign_in

import android.content.Context
import android.widget.Toast
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.core.R
import com.example.core.navigation.Graphs
import com.example.core.ui.theme.AppTheme
import com.example.core.ui.theme.DefaultButtonStyle
import com.example.core.ui.theme.DefaultTextStyle
import com.example.core.ui.utils.ComponentPreview
import com.example.core.ui.utils.ScreenPreview
import com.example.core.ui.utils.Variables
import com.example.domain.models.params.ConnectionParams
import com.example.signin.SignInViewModel
import com.example.signin.domain.states.ConnectionState
import kotlinx.coroutines.launch
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty0


class SignIn {
    @Composable
    fun SignInScreen(
        navController: NavHostController,
        darkMode: MutableState<Boolean> = mutableStateOf(false),
        isConnectionDialogOpened: MutableState<Boolean> = mutableStateOf(false),
        signInViewModel: SignInViewModel = hiltViewModel(),
    ) {
        Content(
            navController = navController,
            darkMode = darkMode,
            isConnectionDialogOpened = isConnectionDialogOpened,

            saveConnectionsFunction = signInViewModel::saveConnections,
            connectionsList = signInViewModel::connectionsList,

            checkConnectionFunction = signInViewModel::checkConnection,
            checkConnectionResult = signInViewModel::checkConnectionResult
        )
    }

    @Composable
    fun Content(
        navController: NavHostController = rememberNavController(),
        darkMode: MutableState<Boolean> = remember { mutableStateOf(false) },
        isConnectionDialogOpened: MutableState<Boolean> = remember { mutableStateOf(false) },

        saveConnectionsFunction: suspend (connectionsList: List<ConnectionParams>) -> Unit = {},
        connectionsList: KMutableProperty0<MutableState<List<ConnectionParams>>>,

        checkConnectionFunction: suspend (url: String) -> Unit = {},
        checkConnectionResult: KProperty0<MutableState<Pair<ConnectionState, Int>>>,
    ) {
        // Default connection resource
        val name = stringResource(R.string.default_connection)
        val selectedConnection: MutableState<ConnectionParams> = remember {
            mutableStateOf(ConnectionParams(name, Variables.DEFAULT_CONNECTION_URL))
        }
        AppTheme(darkMode.value) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
            ) {
                val (
                    titleComponentRef,
                    connectionComponentRef,
                    checkConnectionComponentRef,
                    emailComponentRef,
                    passwordComponentRef,
                    enterComponentRef,
                    offlineComponentRef,
                    rememberComponentRef,
                ) = createRefs()

                /** Dialog (connections) */
                ConnectionsDialog().ConnectionsDialogScreen(
                    isDialogOpened = isConnectionDialogOpened,
                    selectedConnection = selectedConnection,
                    saveDataFunction = saveConnectionsFunction,
                    connectionsList = connectionsList.get(),
                )

                /** Title (header with icon) */
                TitleComponent.Content(
                    modifier = Modifier
                        .constrainAs(titleComponentRef) {
                            linkTo(parent.start, parent.end, bias = 0.5F)
                            linkTo(parent.top, parent.bottom, bias = 0.2F)
                        },
                    isDarkTheme = darkMode
                )

                /** Button (connections)
                 * Click -> show connections dialog */
                ConnectionComponent.Content(
                    modifier = Modifier
                        .constrainAs(connectionComponentRef) {
                            top.linkTo(titleComponentRef.bottom, 60.dp)
                            linkTo(parent.start, parent.end, bias = 0.5F)
                            width = Dimension.percent(0.8F)
                        },
                    isConnectionDialogOpened = isConnectionDialogOpened,
                    selectedConnection = selectedConnection
                )

                /** Text (check connection)
                 * Click -> check current connection to validity */
                CheckConnectionComponent.Content(
                    modifier = Modifier
                        .constrainAs(checkConnectionComponentRef) {
                            top.linkTo(connectionComponentRef.bottom, margin = 3.dp)
                            end.linkTo(connectionComponentRef.end)
                        },
                    selectedConnection = selectedConnection,
                    checkConnection = checkConnectionFunction,
                    checkConnectionResult = checkConnectionResult
                )

                /** TextField (email) */
                val email = remember { mutableStateOf("") }
                EmailComponent.Content(
                    modifier = Modifier
                        .constrainAs(emailComponentRef) {
                            top.linkTo(connectionComponentRef.bottom, margin = 30.dp)
                            linkTo(connectionComponentRef.start, connectionComponentRef.end)
                            width = Dimension.fillToConstraints
                        },
                    email = email
                )

                /** TextField (password) */
                val password = remember { mutableStateOf("") }
                PasswordComponent.Content(
                    modifier = Modifier
                        .constrainAs(passwordComponentRef) {
                            top.linkTo(emailComponentRef.bottom, margin = 10.dp)
                            linkTo(emailComponentRef.start, emailComponentRef.end)
                            width = Dimension.fillToConstraints
                        },
                    password = password,
                )

                /** Checkbox (auto authentication) */
                val autoAuth = remember { mutableStateOf(false) }
                AutoAuthComponent.Content(
                    modifier = Modifier
                        .constrainAs(rememberComponentRef) {
                            bottom.linkTo(enterComponentRef.top)
                            start.linkTo(parent.start, margin = 50.dp)
                        },
                    autoAuth = autoAuth,
                )

                /** Button
                 * Click -> entering */
                EnterComponent.Content(
                    navController = navController,
                    modifier = Modifier
                        .constrainAs(enterComponentRef) {
                            top.linkTo(passwordComponentRef.bottom, margin = 80.dp)
                            linkTo(passwordComponentRef.start, passwordComponentRef.end)
                            width = Dimension.fillToConstraints.atLeastWrapContent
                        },
                    email = remember { mutableStateOf("") },
                    password = password,
                    rememberMe = autoAuth,
                )

                /** Text (offline mode)
                 * Click -> entering with offline mode */
                OfflineModeComponent.Content(
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

    /** PREVIEWS */
    private var emptyConnectionsList: MutableState<List<ConnectionParams>> =
        mutableStateOf(listOf())
    private var checkConnectionResult: MutableState<Pair<ConnectionState, Int>> =
        mutableStateOf(Pair(ConnectionState.WAITING, 0))

    @ScreenPreview
    @Composable
    private fun ScreenPreview() {
        Content(
            connectionsList = this::emptyConnectionsList,
            checkConnectionResult = this::checkConnectionResult
        )
    }

    @ComponentPreview
    @Composable
    private fun ComponentsPreview() {
        AppTheme {
            // Components
        }
    }
}

private class TitleComponent {
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
                HaederFirstPart()
                HeaderSecondPart()
                Icon(isDarkTheme = isDarkTheme)
            }
        }

        @Composable
        private fun HaederFirstPart() {
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

private class ConnectionComponent {
    companion object {
        @Composable
        fun Content(
            modifier: Modifier = Modifier,
            isConnectionDialogOpened: MutableState<Boolean>,
            selectedConnection: MutableState<ConnectionParams>,
        ) {
            OutlinedButton(
                modifier = modifier,
                onClick = { isConnectionDialogOpened.value = true },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.background,
                ),
                border = BorderStroke(1.dp, MaterialTheme.colors.onBackground),
                shape = RoundedCornerShape(25),
            ) {
                ButtonText(selectedConnection = selectedConnection)
            }
        }

        @Composable
        private fun ButtonText(selectedConnection: MutableState<ConnectionParams>) {
            Text(
                text = selectedConnection.value.name,
                fontSize = MaterialTheme.typography.h3.fontSize,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp),
                textAlign = TextAlign.Start
            )
        }
    }
}

private class CheckConnectionComponent {
    companion object {
        @Composable
        fun Content(
            modifier: Modifier,
            selectedConnection: MutableState<ConnectionParams>,
            checkConnection: suspend (String) -> Unit,
            checkConnectionResult: KProperty0<MutableState<Pair<ConnectionState, Int>>>
        ) {
            val scope = rememberCoroutineScope()

            val context = LocalContext.current
            val checkResult = remember { checkConnectionResult.get() }
            SnackBar(
                checkResult = checkResult,
                context = context,
            )

            DefaultTextStyle {
                Text(
                    modifier = modifier
                        .clickable {
                            scope.launch {
                                checkConnection.invoke(
                                    selectedConnection.value.url,
                                )
                            }
                        },
                    text = stringResource(id = R.string.check_connection),
                    color = MaterialTheme.colors.onBackground,
                )
            }
        }

        @Composable
        private fun SnackBar(
            checkResult: MutableState<Pair<ConnectionState, Int>>,
            context: Context,
        ) {
            if (checkResult.value.first == ConnectionState.WAITING) {
                return
            }

            Toast.makeText(
                context,
                stringResource(checkResult.value.first.nameId!!),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

private class EmailComponent {
    companion object {
        @Composable
        fun Content(
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

    }
}

private class PasswordComponent {
    companion object {
        @Composable
        fun Content(
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
    }
}

private class EnterComponent {
    companion object {
        @Composable
        fun Content(
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
    }
}

private class AutoAuthComponent {
    companion object {
        @Composable
        fun Content(
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
    }
}

private class OfflineModeComponent {
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