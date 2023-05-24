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
import com.example.domain.usecases.settings.GetLastAuthorizedUserUseCase
import com.example.domain.usecases.settings.GetUIModeUseCase
import com.example.domain.usecases.settings.SaveUIModeUseCase
import com.example.domain.usecases.signin.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    // Sign in
    private val signInUseCase: SignInUseCase,

    // Connections
    private val checkConnectionUseCase: CheckConnectionUseCase,
    private val getConnectionsUseCase: GetConnectionsUseCase,
    private val saveConnectionsUseCase: SaveConnectionsUseCase,

    // Settings
    private val getUIModeUseCase: GetUIModeUseCase,
    private val saveUIModeUseCase: SaveUIModeUseCase,
    private val getLastAuthorizedUserUseCase: GetLastAuthorizedUserUseCase
) : ViewModel() {
    internal var darkMode: MutableState<Boolean?> = mutableStateOf(null)

    internal var connectionsList = mutableStateOf<List<ConnectionParams>>(listOf())
    internal val checkConnectionResult =
        mutableStateOf(NetworkState.WAIT_FOR_INIT, neverEqualPolicy())

    internal var signInResult: MutableState<Pair<INetworkState?, UserEntity?>> =
        mutableStateOf(Pair(null, null), neverEqualPolicy())

    internal val lastAuthorizedUser: MutableState<UserEntity?> = mutableStateOf(null)

    init {
        viewModelScope.launch { fetchConnections() }
        viewModelScope.launch { fetchUIMode() }
        viewModelScope.launch { fetchLastAuthorizedUser() }
    }

    // Settings
    internal fun changeMode() = viewModelScope.launch {
        saveUIModeUseCase.execute(darkMode.value?.let { !it } ?: false)
        fetchUIMode()
    }

    private suspend fun fetchUIMode() {
        darkMode.value = getUIModeUseCase.execute()
    }

    private fun fetchLastAuthorizedUser() = viewModelScope.launch {
        lastAuthorizedUser.value = getLastAuthorizedUserUseCase.execute()
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
            signInResult.value = signInUseCase.execute(url, Credentials(email, password, ""))
        }
}