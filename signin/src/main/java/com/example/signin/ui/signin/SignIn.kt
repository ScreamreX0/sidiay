package com.example.signin.ui.signin

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
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
import com.example.domain.models.params.ConnectionParams
import com.example.signin.SignInViewModel
import com.example.signin.ui.signin.components.*
import com.example.signin.ui.signin.connections_dialog.ConnectionsDialog
import com.google.gson.Gson
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty0


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

        val context = LocalContext.current
        val success = signInViewModel.signInSuccess
        val errors = signInViewModel.signInErrors

        success.value.let {
            if (it.id != -1L) {
                LaunchedEffect(it) {
                    val jsonUser = Uri.encode(Gson().toJson(it))
                    navController.popBackStack()
                    navController.navigate(
                        route = "${Graphs.HOME}/$jsonUser",
                    )
                }
            }
        }
        errors.value.let {
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

        Content(
            navController = navController,
            darkMode = darkMode,

            saveConnectionsFunction = signInViewModel::saveConnections,
            connectionsList = signInViewModel::connectionsList,

            checkConnectionFunction = signInViewModel::checkConnection,
            checkConnectionResult = signInViewModel::checkConnectionResult,

            signInFunction = signInViewModel::signIn,

            changeUIMode = signInViewModel::changeUIMode,
        )
    }

    @Composable
    fun Content(
        navController: NavHostController = rememberNavController(),
        darkMode: MutableState<String> = remember { mutableStateOf(Constants.LIGHT_MODE) },

        saveConnectionsFunction: suspend (connectionsList: List<ConnectionParams>) -> Unit = {},
        connectionsList: KMutableProperty0<MutableState<List<ConnectionParams>>>,

        checkConnectionFunction: suspend (url: String) -> Unit = {},
        checkConnectionResult: KProperty0<MutableState<ConnectionState>>,

        signInFunction: (String, String, String) -> Unit = { _, _, _ -> },

        changeUIMode: () -> Unit = {}
    ) {
        val defaultConnection = stringResource(R.string.default_connection)
        val selectedConnection: MutableState<ConnectionParams> = remember {
            mutableStateOf(ConnectionParams(defaultConnection, Variables.DEFAULT_CONNECTION_URL))
        }
        val isConnectionDialogOpened: MutableState<Boolean> = remember { mutableStateOf(false) }

        val email = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }
        val autoAuth = remember { mutableStateOf(false) }

        AppTheme(darkMode.value == Constants.DARK_MODE) {
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
                    changeUIMode = changeUIMode
                )

                /** Button (connections)
                 * Click -> show connections dialog */
                Connection.Content(
                    modifier = Modifier
                        .constrainAs(connectionComponentRef) {
                            bottom.linkTo(checkConnectionComponentRef.top, margin = 3.dp)
                            start.linkTo(emailComponentRef.start)
                            end.linkTo(emailComponentRef.end)
                            width = Dimension.fillToConstraints
                        },
                    isConnectionDialogOpened = isConnectionDialogOpened,
                    selectedConnection = selectedConnection
                )

                /** Text (check connection)
                 * Click -> check current connection to validity */
                CheckConnection.Content(
                    modifier = Modifier
                        .constrainAs(checkConnectionComponentRef) {
                            bottom.linkTo(emailComponentRef.top, margin = 3.dp)
                            end.linkTo(emailComponentRef.end)
                        },
                    selectedConnection = selectedConnection,
                    checkConnection = checkConnectionFunction,
                    checkConnectionResult = checkConnectionResult
                )

                /** TextField (email) */
                Email.Content(
                    modifier = Modifier
                        .constrainAs(emailComponentRef) {
                            linkTo(parent.start, parent.end, bias = 0.5F)
                            linkTo(parent.top, parent.bottom, bias = 0.5F)
                            width = Dimension.percent(0.8F)
                        },
                    email = email
                )

                /** TextField (password) */
                Password.Content(
                    modifier = Modifier
                        .constrainAs(passwordComponentRef) {
                            top.linkTo(emailComponentRef.bottom, margin = 10.dp)
                            linkTo(emailComponentRef.start, emailComponentRef.end)
                            width = Dimension.fillToConstraints
                        },
                    password = password,
                )

                /** Checkbox (auto authentication) */
                // TODO
                AutoAuth.Content(
                    modifier = Modifier
                        .constrainAs(rememberComponentRef) {
                            bottom.linkTo(enterComponentRef.top)
                            start.linkTo(enterComponentRef.start)
                        },
                    autoAuth = autoAuth,
                )

                /** Button
                 * Click -> entering */
                Enter.Content(
                    modifier = Modifier
                        .constrainAs(enterComponentRef) {
                            top.linkTo(passwordComponentRef.bottom, margin = 80.dp)
                            linkTo(passwordComponentRef.start, passwordComponentRef.end)
                            width = Dimension.fillToConstraints.atLeastWrapContent
                        },
                    email = email,
                    password = password,
                    selectedConnection = selectedConnection,
                    signInFunction = signInFunction,
                )

                /** Text (offline mode)
                 * Click -> entering with offline mode */
                // TODO
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
    private var checkConnectionResult: MutableState<ConnectionState> =
        mutableStateOf(ConnectionState.WAITING, neverEqualPolicy())
    private var uiMode: MutableState<Boolean>? = mutableStateOf(false)

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