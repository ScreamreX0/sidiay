package com.example.signin.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.atLeastWrapContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.core.navigation.Graphs
import com.example.core.ui.theme.AppTheme
import com.example.core.utils.ApplicationModes
import com.example.core.utils.ConstAndVars
import com.example.core.utils.Helper
import com.example.core.utils.ScreenPreview
import com.example.domain.enums.states.ConnectionState
import com.example.domain.enums.states.SignInStates
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.AuthParams
import com.example.domain.data_classes.params.ConnectionParams
import com.example.signin.ui.components.*
import com.example.signin.ui.components.CheckConnectionComponent
import com.example.signin.ui.components.EnterComponent
import com.example.signin.ui.components.OfflineModeComponent
import com.example.signin.ui.components.PasswordComponent
import com.example.signin.ui.components.TitleComponent


internal class SignIn {
    @Composable
    fun SignInScreen(
        navController: NavHostController,
        signInViewModel: SignInViewModel = hiltViewModel(),
    ) {
        val darkMode = signInViewModel.darkMode
        if (darkMode.value == null) return

        val success = signInViewModel.signInSuccess
        val errors = signInViewModel.signInError
        val connectionsList = signInViewModel.connectionsList
        val checkConnectionResult = signInViewModel.checkConnectionResult

        AppTheme(darkMode.value!!) {
            Content(
                navController = navController,
                connectionsList = connectionsList,
                signInSuccess = success,
                signInErrors = errors,
                checkConnectionResult = checkConnectionResult,
                darkMode = darkMode,

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
        signInSuccess: MutableState<UserEntity> = remember { mutableStateOf(UserEntity(id = 0)) },
        signInErrors: MutableState<String?> = remember { mutableStateOf(null) },
        isConnectionDialogOpened: MutableState<Boolean> = remember { mutableStateOf(false) },
        checkConnectionResult: MutableState<ConnectionState> = remember { mutableStateOf(ConnectionState.WAITING) },
        darkMode: MutableState<Boolean?> = remember { mutableStateOf(false) },
        checkConnectionFunction: suspend (url: String) -> Unit = {},
        updateConnectionsListFunction: suspend () -> Unit = {},
        saveConnectionsFunction: suspend (connectionsList: List<ConnectionParams>) -> Unit = {},
        changeUIModeFunction: () -> Unit = {},
        signInFunction: (String, String, String) -> Unit = { _, _, _ -> },
    ) {
        val context = LocalContext.current
        val selectedConnection = remember {
            mutableStateOf(ConnectionParams("Стандартное соединение", ConstAndVars.URL))
        }

        ConstraintLayout(
            constraintSet = getConstraints(),
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            signInSuccess.value.let { currentUser ->
                if (currentUser.id == -1L) return@let
                val authParams = AuthParams(
                    user = if (ConstAndVars.APPLICATION_MODE == ApplicationModes.DEBUG_AND_OFFLINE) UserEntity(0) else currentUser,
                    connectionParams = selectedConnection.value,
                    darkMode = darkMode.value,
                )

                LaunchedEffect(currentUser) {
                    val authParamsString = Helper.parcelableToString(authParams)
                    navController.navigate(route = "${Graphs.MAIN_MENU}/$authParamsString")
                }
            }
            signInErrors.value?.let { Helper.showShortToast(context, it) }

            // Dialog
            ConnectionsDialog(
                isDialogOpened = isConnectionDialogOpened,
                selectedConnection = selectedConnection,
                saveConnectionsFunction = saveConnectionsFunction,
                connectionsList = connectionsList,
                updateConnectionsList = updateConnectionsListFunction
            )

            // Title
            TitleComponent(
                modifier = Modifier.layoutId("titleComponentRef"),
                changeUIMode = changeUIModeFunction
            )

            // Default connection button
            ConnectionComponent(
                modifier = Modifier.layoutId("connectionComponentRef"),
                isConnectionDialogOpened = isConnectionDialogOpened,
                selectedConnection = selectedConnection
            )

            // Check connection
            CheckConnectionComponent(
                modifier = Modifier.layoutId("checkConnectionComponentRef"),
                selectedConnection = selectedConnection,
                checkConnection = checkConnectionFunction,
                checkConnectionResult = checkConnectionResult
            )

            // Email
            val email = remember { mutableStateOf("") }
            EmailComponent(
                modifier = Modifier.layoutId("emailComponentRef"),
                email = email
            )

            // Password
            val password = remember { mutableStateOf("") }
            PasswordComponent(
                modifier = Modifier.layoutId("passwordComponentRef"),
                password = password,
            )

            // Auto auth
            // TODO("Add auto auth function")
            val autoAuth = remember { mutableStateOf(false) }
            AutoAuthComponent(
                modifier = Modifier.layoutId("rememberComponentRef"),
                autoAuth = autoAuth,
            )

            // Enter
            EnterComponent(
                modifier = Modifier.layoutId("enterComponentRef"),
                email = email,
                password = password,
                selectedConnection = selectedConnection,
                signInFunction = signInFunction,
            )

            // Offline mode
            OfflineModeComponent(
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

    @ScreenPreview
    @Composable
    private fun ScreenPreview() {
        AppTheme(isSystemInDarkTheme()) { Content() }
    }
}