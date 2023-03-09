package com.example.signin

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.ui.utils.Constants
import com.example.core.ui.utils.Debugger
import com.example.domain.enums.states.SignInStates
import com.example.domain.models.entities.UserEntity
import com.example.domain.models.params.ConnectionParams
import com.example.domain.models.params.Credentials
import com.example.domain.usecases.signin.CheckSignInFieldsUseCase
import com.example.data.datastore.ConnectionsDataStore
import com.example.data.datastore.ThemeDataStore
import com.example.domain.enums.states.ConnectionState
import com.example.domain.enums.states.LoadingState
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
    internal var darkMode: MutableState<String> = mutableStateOf(Constants.NULL)

    internal var connectionsList = mutableStateOf<List<ConnectionParams>>(listOf())
    internal val checkConnectionResult = mutableStateOf(ConnectionState.WAITING, neverEqualPolicy())

    internal var signInErrors: MutableState<List<SignInStates>> = mutableStateOf(listOf())
    internal var signInSuccess: MutableState<UserEntity> = mutableStateOf(UserEntity())

    init {
        viewModelScope.launch { updateConnectionsVar() }
        viewModelScope.launch { updateUIModeVar() }
    }

    /** UIMode */
    internal fun changeUIMode() {
        viewModelScope.launch {
            themeDataStore.saveMode(
                if (darkMode.value == Constants.LIGHT_MODE) {
                    Constants.DARK_MODE
                } else {
                    Constants.LIGHT_MODE
                }
            )
            updateUIModeVar()
        }
    }

    private suspend fun updateUIModeVar() {
        darkMode.value = themeDataStore.getMode.first()
    }

    /** Connections */
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
        connectionsList.value =
            connectionsDataStore.getConnections.first() as List<ConnectionParams>
    }

    /** SignIn */
    internal fun signIn(url: String, email: String, password: String) {
        val params = Credentials(email, password)
        val result = checkFieldsUseCase.execute(params = params)
        if (result.size != 0 && Constants.CHECK_SIGN_IN_FIELDS) {
            signInErrors.value = result
            return
        }

        if (Constants.DEBUG_MODE) {
            Debugger.Companion.printInfo("DEBUG MODE ENABLED")
            signInOffline()
        } else {
            Debugger.Companion.printInfo("Online sign in. IP:${Constants.URL}")
            signInOnline(url = url, params = params)
        }


    }

    private fun signInOnline(url: String, params: Credentials) {
        viewModelScope.launch(getSignInHandler()) {
            val signInResult = signInUseCase.execute(url, params)

            when (signInResult.first) {
                HttpURLConnection.HTTP_OK -> {
                    signInResult.second?.let { signInSuccess.value = it }
                }
                HttpURLConnection.HTTP_UNAUTHORIZED -> {
                    signInErrors.value = listOf(SignInStates.WRONG_CREDENTIALS_FORMAT)
                }
                else -> {
                    Debugger.Companion.printInfo("Response code in sign in - ${signInResult.first} (unhandled)")
                    signInErrors.value = listOf(SignInStates.NO_SERVER_CONNECTION)
                }
            }
        }
    }

    private fun signInOffline() {
        signInSuccess.value = UserEntity(id = 1)
    }

    private fun getSignInHandler() = CoroutineExceptionHandler { _, throwable ->
        if (throwable::class == ConnectException::class) this.signInErrors.value =
            listOf(SignInStates.NO_SERVER_CONNECTION)
    }
}