package com.example.signin.ui.signin

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.atLeastWrapContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.core.R
import com.example.core.navigation.Graphs
import com.example.core.ui.theme.AppTheme
import com.example.core.ui.utils.ComponentPreview
import com.example.core.ui.utils.Constants
import com.example.core.ui.utils.ScreenPreview
import com.example.core.ui.utils.Variables
import com.example.domain.enums.states.ConnectionState
import com.example.domain.enums.states.SignInStates
import com.example.domain.models.entities.UserEntity
import com.example.domain.models.params.AuthParams
import com.example.domain.models.params.ConnectionParams
import com.example.signin.SignInViewModel
import com.example.signin.ui.signin.components.*
import com.example.signin.ui.signin.connections_dialog.ConnectionsDialog
import com.google.gson.Gson


internal class SignIn {
    @Composable
    fun SignInScreen(
        navController: NavHostController,
        signInViewModel: SignInViewModel = hiltViewModel(),
    ) {
        val darkMode = signInViewModel.darkMode
        if (darkMode.value == Constants.NULL) {
            return
        }

        val success = signInViewModel.signInSuccess
        val errors = signInViewModel.signInErrors
        val connectionsList = signInViewModel.connectionsList
        val checkConnectionResult = signInViewModel.checkConnectionResult

        AppTheme(darkMode.value == Constants.DARK_MODE) {
            Content(
                navController = navController,
                connectionsList = connectionsList,
                signInSuccess = success,
                signInErrors = errors,
                checkConnectionResult = checkConnectionResult,

                checkConnectionFunction = signInViewModel::checkConnection,
                updateConnectionsListFunction = signInViewModel::updateConnectionsVar,
                saveConnectionsFunction = signInViewModel::saveConnections,
                changeUIModeFunction = signInViewModel::changeUIMode,
                signInFunction = signInViewModel::signIn,
            )
        }
    }

    @Composable
    fun Content(
        navController: NavHostController = rememberNavController(),
        connectionsList: MutableState<List<ConnectionParams>> = mutableStateOf(listOf()),
        signInSuccess: MutableState<UserEntity> = remember { mutableStateOf(UserEntity()) },
        signInErrors: MutableState<List<SignInStates>> = remember { mutableStateOf(listOf()) },
        isConnectionDialogOpened: MutableState<Boolean> = remember { mutableStateOf(false) },
        checkConnectionResult: MutableState<ConnectionState> = remember { mutableStateOf(ConnectionState.WAITING) },

        checkConnectionFunction: suspend (url: String) -> Unit = {},
        updateConnectionsListFunction: suspend () -> Unit = {},
        saveConnectionsFunction: suspend (connectionsList: List<ConnectionParams>) -> Unit = {},
        changeUIModeFunction: () -> Unit = {},
        signInFunction: (String, String, String) -> Unit = { _, _, _ -> },
    ) {
        val context = LocalContext.current
        val defaultConnection = stringResource(R.string.default_connection)
        val selectedConnection = remember {
            mutableStateOf(ConnectionParams(defaultConnection, Variables.DEFAULT_CONNECTION_URL))
        }

        ConstraintLayout(
            constraintSet = getConstraints(),
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            signInSuccess.value.let {
                if (it.id != -1L) {
                    LaunchedEffect(it) {
                        val authParams = Uri.encode(
                            Gson().toJson(
                                AuthParams(
                                    user = it,
                                    url = selectedConnection.value,
                                    darkMode = false

                                )
                            )
                        )
                        navController.popBackStack()
                        navController.navigate(
                            route = "${Graphs.MAIN_MENU}/$authParams",
                        )
                    }
                }
            }
            signInErrors.value.let {
                if (it.contains(SignInStates.NO_SERVER_CONNECTION)) {
                    Toast.makeText(
                        context,
                        stringResource(R.string.no_server_connection),
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (it.contains(SignInStates.SHORT_OR_LONG_EMAIL)) {
                    Toast.makeText(
                        context,
                        stringResource(R.string.short_or_long_email),
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (it.contains(SignInStates.SHORT_OR_LONG_PASSWORD)) {
                    Toast.makeText(
                        context,
                        stringResource(R.string.short_or_long_password),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            /** Dialog (connections) */
            ConnectionsDialog.ConnectionsDialogScreen(
                isDialogOpened = isConnectionDialogOpened,
                selectedConnection = selectedConnection,
                saveConnectionsFunction = saveConnectionsFunction,
                connectionsList = connectionsList,
                updateConnectionsList = updateConnectionsListFunction
            )

            /** Title (header with icon) */
            TitleComponent.Content(
                modifier = Modifier.layoutId("titleComponentRef"),
                changeUIMode = changeUIModeFunction
            )

            /** Button (connections)
             * Click -> show connections dialog */
            ConnectionComponent.Content(
                modifier = Modifier.layoutId("connectionComponentRef"),
                isConnectionDialogOpened = isConnectionDialogOpened,
                selectedConnection = selectedConnection
            )

            /** Text (check connection)
             * Click -> check current connection to validity */
            CheckConnectionComponent.Content(
                modifier = Modifier.layoutId("checkConnectionComponentRef"),
                selectedConnection = selectedConnection,
                checkConnection = checkConnectionFunction,
                checkConnectionResult = checkConnectionResult
            )

            /** TextField (email) */
            val email = remember { mutableStateOf("") }
            EmailComponent.Content(
                modifier = Modifier.layoutId("emailComponentRef"),
                email = email
            )

            /** TextField (password) */
            val password = remember { mutableStateOf("") }
            PasswordComponent.Content(
                modifier = Modifier.layoutId("passwordComponentRef"),
                password = password,
            )

            /** Checkbox (auto authentication) */
            // TODO
            val autoAuth = remember { mutableStateOf(false) }
            AutoAuthComponent.Content(
                modifier = Modifier.layoutId("rememberComponentRef"),
                autoAuth = autoAuth,
            )

            /** Button
             * Click -> entering */
            EnterComponent.Content(
                modifier = Modifier.layoutId("enterComponentRef"),
                email = email,
                password = password,
                selectedConnection = selectedConnection,
                signInFunction = signInFunction,
            )

            /** Text (offline mode)
             * Click -> entering with offline mode */
            // TODO
            OfflineModeComponent.Content(
                navController = navController,
                modifier = Modifier.layoutId("offlineComponentRef"),
            )

        }
    }

    private fun getConstraints() = ConstraintSet {
        val titleComponentRef = createRefFor("titleComponentRef")
        val connectionComponentRef = createRefFor("connectionComponentRef")
        val checkConnectionComponentRef = createRefFor("checkConnectionComponentRef")
        val emailComponentRef = createRefFor("emailComponentRef")
        val passwordComponentRef = createRefFor("passwordComponentRef")
        val enterComponentRef = createRefFor("enterComponentRef")
        val offlineComponentRef = createRefFor("offlineComponentRef")
        val rememberComponentRef = createRefFor("rememberComponentRef")

        constrain(titleComponentRef) {
            linkTo(parent.start, parent.end, bias = 0.5F)
            linkTo(parent.top, parent.bottom, bias = 0.2F)
        }
        constrain(connectionComponentRef) {
            bottom.linkTo(checkConnectionComponentRef.top, margin = 3.dp)
            start.linkTo(emailComponentRef.start)
            end.linkTo(emailComponentRef.end)
            width = Dimension.fillToConstraints
        }
        constrain(emailComponentRef) {
            linkTo(parent.start, parent.end, bias = 0.5F)
            linkTo(parent.top, parent.bottom, bias = 0.5F)
            width = Dimension.percent(0.8F)
        }
        constrain(checkConnectionComponentRef) {
            bottom.linkTo(emailComponentRef.top, margin = 3.dp)
            end.linkTo(emailComponentRef.end)
        }
        constrain(passwordComponentRef) {
            top.linkTo(emailComponentRef.bottom, margin = 10.dp)
            linkTo(emailComponentRef.start, emailComponentRef.end)
            width = Dimension.fillToConstraints
        }
        constrain(rememberComponentRef) {
            bottom.linkTo(enterComponentRef.top)
            start.linkTo(enterComponentRef.start)
        }
        constrain(enterComponentRef) {
            top.linkTo(passwordComponentRef.bottom, margin = 80.dp)
            linkTo(passwordComponentRef.start, passwordComponentRef.end)
            width = Dimension.fillToConstraints.atLeastWrapContent
        }
        constrain(offlineComponentRef) {
            linkTo(enterComponentRef.start, enterComponentRef.end, bias = 0.5F)
            top.linkTo(enterComponentRef.bottom, margin = 10.dp)
        }
    }

    /** PREVIEWS */
    @ScreenPreview
    @Composable
    private fun ScreenPreview() {
        AppTheme(isSystemInDarkTheme()) {
            Content()
        }
    }

    @ComponentPreview
    @Composable
    private fun ComponentsPreview() {
        AppTheme {
            // Components
        }
    }
}