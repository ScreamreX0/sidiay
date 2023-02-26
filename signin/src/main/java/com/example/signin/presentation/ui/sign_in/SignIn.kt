package com.example.signin.presentation.ui.sign_in

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.atLeastWrapContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.core.R
import com.example.core.ui.theme.AppTheme
import com.example.core.ui.utils.ComponentPreview
import com.example.core.ui.utils.ScreenPreview
import com.example.core.ui.utils.Variables
import com.example.domain.models.params.ConnectionParams
import com.example.signin.SignInViewModel
import com.example.signin.domain.states.ConnectionState
import com.example.signin.presentation.ui.sign_in.components.*
import com.example.signin.presentation.ui.sign_in.connections_dialog.ConnectionsDialog
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty0


internal class SignIn {
    @Composable
    fun SignInScreen(
        navController: NavHostController,
        darkMode: MutableState<Boolean> = mutableStateOf(false),
        signInViewModel: SignInViewModel = hiltViewModel(),
    ) {
        Content(
            navController = navController,
            darkMode = darkMode,

            saveConnectionsFunction = signInViewModel::saveConnections,
            connectionsList = signInViewModel::connectionsList,

            checkConnectionFunction = signInViewModel::checkConnection,
            checkConnectionResult = signInViewModel::checkConnectionResult,

            signInFunction = signInViewModel::signIn
        )
    }

    @Composable
    fun Content(
        navController: NavHostController = rememberNavController(),
        darkMode: MutableState<Boolean> = remember { mutableStateOf(false) },

        saveConnectionsFunction: suspend (connectionsList: List<ConnectionParams>) -> Unit = {},
        connectionsList: KMutableProperty0<MutableState<List<ConnectionParams>>>,

        checkConnectionFunction: suspend (url: String) -> Unit = {},
        checkConnectionResult: KProperty0<MutableState<Pair<ConnectionState, Int>>>,

        signInFunction: (String, String, String) -> Unit = {_, _, _ ->},
    ) {
        val defaultConnection = stringResource(R.string.default_connection)
        val selectedConnection: MutableState<ConnectionParams> = remember {
            mutableStateOf(ConnectionParams(defaultConnection, Variables.DEFAULT_CONNECTION_URL))
        }
        val isConnectionDialogOpened: MutableState<Boolean> = remember { mutableStateOf(false) }
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
                ConnectionsDialog.ConnectionsDialogScreen(
                    isDialogOpened = isConnectionDialogOpened,
                    selectedConnection = selectedConnection,
                    saveDataFunction = saveConnectionsFunction,
                    connectionsList = connectionsList.get(),
                )

                /** Title (header with icon) */
                Title.Content(
                    modifier = Modifier
                        .constrainAs(titleComponentRef) {
                            linkTo(parent.start, parent.end, bias = 0.5F)
                            linkTo(parent.top, parent.bottom, bias = 0.2F)
                        },
                    isDarkTheme = darkMode
                )

                /** Button (connections)
                 * Click -> show connections dialog */
                Connection.Content(
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
                CheckConnection.Content(
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
                Email.Content(
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
                Password.Content(
                    modifier = Modifier
                        .constrainAs(passwordComponentRef) {
                            top.linkTo(emailComponentRef.bottom, margin = 10.dp)
                            linkTo(emailComponentRef.start, emailComponentRef.end)
                            width = Dimension.fillToConstraints
                        },
                    password = password,
                )

                /* /** Checkbox (auto authentication) */
                val autoAuth = remember { mutableStateOf(false) }
                AutoAuthComponent.Content(
                    modifier = Modifier
                        .constrainAs(rememberComponentRef) {
                            bottom.linkTo(enterComponentRef.top)
                            start.linkTo(parent.start, margin = 50.dp)
                        },
                    autoAuth = autoAuth,
                ) */

                /** Button
                 * Click -> entering */
                Enter.Content(
                    navController = navController,
                    modifier = Modifier
                        .constrainAs(enterComponentRef) {
                            top.linkTo(passwordComponentRef.bottom, margin = 80.dp)
                            linkTo(passwordComponentRef.start, passwordComponentRef.end)
                            width = Dimension.fillToConstraints.atLeastWrapContent
                        },
                    email = email,
                    password = password,
                    selectedConnection = selectedConnection,
                    signInFunction = signInFunction
                )

                /** Text (offline mode)
                 * Click -> entering with offline mode */
                OfflineMode.Content(
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
            checkConnectionResult = this::checkConnectionResult,
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