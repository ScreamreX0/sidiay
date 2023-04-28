package com.example.signin.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.utils.ConstAndVars
import com.example.core.utils.ApplicationModes
import com.example.core.utils.Logger
import com.example.domain.enums.states.SignInStates
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.ConnectionParams
import com.example.domain.data_classes.params.Credentials
import com.example.domain.usecases.signin.CheckSignInFieldsUseCase
import com.example.data.datastore.ConnectionsDataStore
import com.example.data.datastore.ThemeDataStore
import com.example.domain.enums.states.ConnectionState
import com.example.domain.usecases.signin.CheckConnectionUseCase
import com.example.domain.usecases.signin.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.HttpURLConnection
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    // UseCases
    private val checkFieldsUseCase: CheckSignInFieldsUseCase,
    private val signInUseCase: SignInUseCase,
    private val checkConnectionUseCase: CheckConnectionUseCase,

    // Stores
    private val connectionsDataStore: ConnectionsDataStore,
    private val themeDataStore: ThemeDataStore,
) : ViewModel() {
    internal var darkMode: MutableState<Boolean?> = mutableStateOf(null)

    internal var connectionsList = mutableStateOf<List<ConnectionParams>>(listOf())
    internal val checkConnectionResult = mutableStateOf(ConnectionState.WAITING, neverEqualPolicy())

    internal var signInError: MutableState<String?> = mutableStateOf(null, neverEqualPolicy())
    internal var signInSuccess: MutableState<UserEntity> = mutableStateOf(UserEntity(-1))

    init {
        viewModelScope.launch { updateConnectionsVar() }
        viewModelScope.launch { updateUIModeVar() }
    }

    // UIMode
    internal fun changeUIMode() {
        viewModelScope.launch {
            themeDataStore.saveMode(darkMode.value?.let { !it } ?: true)
            updateUIModeVar()
        }
    }

    private suspend fun updateUIModeVar() {
        darkMode.value = themeDataStore.getMode.first().toBoolean()
    }

    // Connections
    internal suspend fun checkConnection(url: String) {
        if (url.last().toString() != "/") {
            url.plus("/")
        }

        checkConnectionResult.value = if (checkConnectionUseCase.execute(url = url)) {
            ConnectionState.ESTABLISHED
        } else {
            ConnectionState.NOT_ESTABLISHED
        }
    }

    internal suspend fun saveConnections(connectionsList: List<ConnectionParams>) {
        connectionsDataStore.saveConnections(connectionsList)
    }

    internal suspend fun updateConnectionsVar() {
        val connections = connectionsDataStore.getConnections.first()

        connectionsList.value = if (connections == null) {
            listOf()
        } else {
            connections as List<ConnectionParams>
        }

    }

    // Sign in
    internal fun signIn(url: String, email: String, password: String) {
        if (ConstAndVars.APPLICATION_MODE == ApplicationModes.DEBUG_AND_OFFLINE) {
            Logger.Companion.m("DEBUG_AND_OFFLINE MODE ENABLED")
            signInSuccess.value = UserEntity(id = 0)
            return
        }

        var params = Credentials(email, password)

        if (ConstAndVars.APPLICATION_MODE == ApplicationModes.DEBUG_AND_ONLINE) {
            Logger.Companion.m("DEBUG_AND_ONLINE MODE ENABLED")
            params = Credentials(ConstAndVars.DEBUG_MODE_EMAIL, ConstAndVars.DEBUG_MODE_PASSWORD)
        } else {
            checkFieldsUseCase.execute(params = params)?.let {
                signInError.value = it
                return
            }
        }

        Logger.Companion.m("Online sign in. IP:${ConstAndVars.URL}")
        viewModelScope.launch(getSignInHandler()) {
            val signInResult = signInUseCase.execute(url, params)
            signInResult.first?.let { signInError.value = it }
                ?: signInResult.second?.let { signInSuccess.value = it }
        }
    }

    private fun getSignInHandler() = CoroutineExceptionHandler { _, throwable ->
        if (throwable::class == ConnectException::class) signInError.value =
            SignInStates.NO_SERVER_CONNECTION.title
    }
}