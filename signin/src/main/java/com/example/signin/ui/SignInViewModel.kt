package com.example.signin.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.utils.Helper
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.ConnectionParams
import com.example.domain.data_classes.params.Credentials
import com.example.domain.enums.states.INetworkState
import com.example.domain.enums.states.NetworkState
import com.example.domain.usecases.connections.CheckConnectionUseCase
import com.example.domain.usecases.connections.GetConnectionsUseCase
import com.example.domain.usecases.connections.SaveConnectionsUseCase
import com.example.domain.usecases.settings.GetSettingsUseCase
import com.example.domain.usecases.settings.SaveSettingsUseCase
import com.example.domain.usecases.signin.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val checkConnectionUseCase: CheckConnectionUseCase,
    private val getConnectionsUseCase: GetConnectionsUseCase,
    private val saveConnectionsUseCase: SaveConnectionsUseCase,
    private val getSettingsUseCase: GetSettingsUseCase,
    private val saveSettingsUseCase: SaveSettingsUseCase
) : ViewModel() {
    internal var darkMode: MutableState<Boolean?> = mutableStateOf(null)

    internal var connectionsList = mutableStateOf<List<ConnectionParams>>(listOf())
    internal val checkConnectionResult =
        mutableStateOf(NetworkState.WAIT_FOR_INIT, neverEqualPolicy())

    internal var signInResult: MutableState<Pair<INetworkState?, UserEntity?>> =
        mutableStateOf(Pair(null, null), neverEqualPolicy())

    init {
        viewModelScope.launch { fetchConnections() }
        viewModelScope.launch { fetchUIMode() }
    }

    // UI mode
    internal fun changeMode() = viewModelScope.launch {
        saveSettingsUseCase.execute(darkMode.value?.let { !it } ?: false)
        fetchUIMode()
    }

    private suspend fun fetchUIMode() {
        darkMode.value = getSettingsUseCase.execute()
    }

    // Connections
    internal suspend fun checkConnection(url: String) {
        checkConnectionResult.value = checkConnectionUseCase.execute(url)
    }

    internal suspend fun saveConnections(connectionsList: List<ConnectionParams>) =
        saveConnectionsUseCase.execute(connectionsList)

    internal suspend fun fetchConnections() {
        connectionsList.value = getConnectionsUseCase.execute()
    }

    // Sign in
    internal fun signIn(url: String?, email: String, password: String) =
        viewModelScope.launch(Helper.getCoroutineNetworkExceptionHandler {
            signInResult.value = Pair(NetworkState.NO_SERVER_CONNECTION, null)
        }) {
            signInResult.value = signInUseCase.execute(url, Credentials(email, password))
        }
}