package com.example.signin.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDirection.Companion.Content
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.atLeastWrapContent
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.ui.theme.AppTheme
import com.example.core.utils.ApplicationModes
import com.example.core.utils.Constants
import com.example.core.utils.Helper
import com.example.core.utils.ScreenPreview
import com.example.domain.data_classes.entities.EmployeeEntity
import com.example.domain.enums.states.NetworkState
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.AuthParams
import com.example.domain.data_classes.params.ConnectionParams
import com.example.domain.enums.JobTitlesEnum
import com.example.signin.ui.components.*
import com.example.signin.ui.components.CheckConnectionComponent
import com.example.signin.ui.components.EnterComponent
import com.example.signin.ui.components.OfflineModeComponent
import com.example.signin.ui.components.PasswordComponent
import com.example.signin.ui.components.TitleComponent


internal class SignIn {
    @Composable
    fun SignInScreen(
        navigateToMainMenu: (String) -> Unit = { _ -> },
        navigateToMainMenuOfflineMode: (UserEntity) -> Unit = {},
        signInViewModel: SignInViewModel = hiltViewModel(),
    ) {
        val darkMode = signInViewModel.darkMode
        if (darkMode.value == null) return

        val signInResult = signInViewModel.signInResult
        val connectionsList = signInViewModel.connectionsList
        val checkConnectionResult = signInViewModel.checkConnectionResult
        val context = LocalContext.current
        val selectedConnection: MutableState<ConnectionParams?> =
            remember { mutableStateOf(ConnectionParams()) }

        // Signing in
        signInResult.value.let { result ->
            result.second?.let { itUser ->
                // Sign in success
                val authParams = AuthParams(
                    user = if (Constants.APPLICATION_MODE == ApplicationModes.OFFLINE) {
                        UserEntity(id = 0, employee = EmployeeEntity(id = 0, jobTitle = JobTitlesEnum.CHIEF_ENGINEER.value))
                    } else { itUser },
                    connectionParams = selectedConnection.value,
                    darkMode = darkMode.value,
                )

                LaunchedEffect(itUser) {
                    val authParamsString = Helper.parcelableToString(authParams)
                    navigateToMainMenu(authParamsString)
                }
            }
            result.first?.let {
                // Sign in error
                    itMessage ->
                Helper.showShortToast(context, itMessage.toString())
            }
        }

        AppTheme(darkMode.value!!) {
            Content(
                selectedConnection = selectedConnection,
                connectionsList = connectionsList,
                checkConnectionResult = checkConnectionResult,
                checkConnectionFunction = signInViewModel::checkConnection,
                updateConnectionsListFunction = signInViewModel::fetchConnections,
                saveConnectionsFunction = signInViewModel::saveConnections,
                changeUIModeFunction = signInViewModel::changeMode,
                signInFunction = signInViewModel::signIn,
                navigateToMainMenuOfflineMode = {
                    signInViewModel.lastAuthorizedUser.value?.let { itUser ->
                        navigateToMainMenuOfflineMode(itUser)
                    } ?: run {
                        Helper.showLongToast(context, "Для того чтобы войти в автономный режим нужно хотя бы раз авторизоваться")
                    }
                },
            )
        }
    }

    @Composable
    fun Content(
        // Connections
        connectionsList: MutableState<List<ConnectionParams>> = mutableStateOf(listOf()),
        isConnectionDialogOpened: MutableState<Boolean> = mutableStateOf(false),
        checkConnectionResult: MutableState<NetworkState> = mutableStateOf(NetworkState.WAIT_FOR_INIT),
        checkConnectionFunction: suspend (url: String) -> Unit = { _ -> },
        updateConnectionsListFunction: suspend () -> Unit = {},
        saveConnectionsFunction: suspend (connectionsList: List<ConnectionParams>) -> Unit = {},
        selectedConnection: MutableState<ConnectionParams?> = mutableStateOf(null),

        // Settings
        changeUIModeFunction: () -> Unit = {},

        // Sign in
        signInFunction: (String?, String, String) -> Unit = { _, _, _ -> },
        navigateToMainMenuOfflineMode: () -> Unit = {},
    ) {
        ConstraintLayout(
            constraintSet = getConstraints(), modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            // Connections dialog
            ConnectionsDialog(
                isDialogOpened = isConnectionDialogOpened,
                selectedConnection = selectedConnection,
                saveConnectionsFunction = saveConnectionsFunction,
                connectionsList = connectionsList,
                updateConnectionsList = updateConnectionsListFunction
            )

            // App title
            TitleComponent(
                modifier = Modifier.layoutId("titleComponentRef"),
                changeUIMode = changeUIModeFunction
            )

            // Connections button
            ConnectionComponent(
                modifier = Modifier.layoutId("connectionComponentRef"),
                isConnectionDialogOpened = isConnectionDialogOpened,
                selectedConnection = selectedConnection
            )

            // Check connection button
            CheckConnectionComponent(
                modifier = Modifier.layoutId("checkConnectionComponentRef"),
                selectedConnection = selectedConnection,
                checkConnection = checkConnectionFunction,
                checkConnectionResult = checkConnectionResult
            )

            // Email text field
            val email = remember { mutableStateOf("") }
            EmailComponent(
                modifier = Modifier.layoutId("emailComponentRef"), email = email
            )

            // Password text field
            val password = remember { mutableStateOf("") }
            PasswordComponent(
                modifier = Modifier.layoutId("passwordComponentRef"),
                password = password,
            )

            // Enter button
            EnterComponent(
                modifier = Modifier.layoutId("enterComponentRef"),
                email = email,
                password = password,
                selectedConnection = selectedConnection,
                signInFunction = signInFunction,
            )

            // Offline mode button
            OfflineModeComponent(
                modifier = Modifier.layoutId("offlineComponentRef"),
                navigateToMainMenuOfflineMode = navigateToMainMenuOfflineMode
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