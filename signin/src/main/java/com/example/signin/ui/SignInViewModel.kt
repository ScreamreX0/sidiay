package com.example.signin.ui

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.utils.ConstAndVars
import com.example.core.utils.ApplicationModes
import com.example.core.utils.Helper
import com.example.core.utils.Logger
import com.example.domain.enums.states.SignInStates
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.ConnectionParams
import com.example.domain.data_classes.params.Credentials
import com.example.domain.usecases.signin.CheckSignInFieldsUseCase
import com.example.data.datastore.ConnectionsDataStore
import com.example.data.datastore.ThemeDataStore
import com.example.domain.enums.states.NetworkConnectionState
import com.example.domain.usecases.signin.CheckConnectionUseCase
import com.example.domain.usecases.signin.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.net.ConnectException
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
    internal val checkConnectionResult = mutableStateOf(NetworkConnectionState.WAITING, neverEqualPolicy())

    internal var signInResult: MutableState<Pair<String?, UserEntity?>> = mutableStateOf(Pair(null, null), neverEqualPolicy())

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
    internal suspend fun checkConnection(url: String?, context: Context) {
        checkConnectionResult.value = checkConnectionUseCase.execute(url = url, context)
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
    internal fun signIn(url: String?, email: String, password: String) {
        if (ConstAndVars.APPLICATION_MODE == ApplicationModes.DEBUG_AND_OFFLINE) {
            Logger.Companion.m("DEBUG_AND_OFFLINE MODE ENABLED")
            signInResult.value = Pair(null, UserEntity(id = 0))
            return
        }

        var params = Credentials(email, password)

        if (ConstAndVars.APPLICATION_MODE == ApplicationModes.DEBUG_AND_ONLINE) {
            Logger.Companion.m("DEBUG_AND_ONLINE MODE ENABLED")
            params = Credentials(ConstAndVars.DEBUG_MODE_EMAIL, ConstAndVars.DEBUG_MODE_PASSWORD)
        } else {
            checkFieldsUseCase.execute(params = params)?.let {
                signInResult.value = Pair(it, null)
                return
            }
        }

        Logger.Companion.m("Online sign in. IP:${ConstAndVars.URL}")
        viewModelScope.launch(
            Helper.getCoroutineNetworkExceptionHandler {
                signInResult.value = Pair(SignInStates.NO_SERVER_CONNECTION.title, null)
            }
        ) {
            signInResult.value = signInUseCase.execute(url, params)
        }
    }
}