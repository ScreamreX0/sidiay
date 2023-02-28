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
import com.example.data.datastores.ConnectionsDataStore
import com.example.domain.enums.states.ConnectionState
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
    private val checkFieldsUseCase: CheckSignInFieldsUseCase,
    private val signInUseCase: SignInUseCase,
    private val connectionsDataStore: ConnectionsDataStore,
    private val checkConnectionUseCase: CheckConnectionUseCase
) : ViewModel() {
    /** Connections */
    var connectionsList = mutableStateOf<List<ConnectionParams>>(listOf())

    init {
        viewModelScope.launch {
            connectionsList = mutableStateOf(
                connectionsDataStore.getConnections.first() as List<ConnectionParams>
            )
        }
    }

    val checkConnectionResult = mutableStateOf(ConnectionState.WAITING, neverEqualPolicy())
    suspend fun checkConnection(url: String) {
        if (url.last().toString() != "/") {
            url.plus("/")
        }

        checkConnectionResult.value = if (checkConnectionUseCase.execute(url = url)) {
            ConnectionState.ESTABLISHED
        } else {
            ConnectionState.NOT_ESTABLISHED
        }
    }

    suspend fun saveConnections(connectionsList: List<ConnectionParams>) {
        connectionsDataStore.saveConnections(connectionsList)
    }

    /** SignIn */
    var signInErrors: MutableState<List<SignInStates>?> = mutableStateOf(null, neverEqualPolicy())
    var signInSuccess: MutableState<UserEntity?> = mutableStateOf(null, neverEqualPolicy())
    fun signIn(url: String, email: String, password: String) {
        val params = Credentials(email, password)
        val result = checkFieldsUseCase.execute(params = params)
        if (result.size != 0 && Constants.CHECK_SIGN_IN_FIELDS) {
            this.signInErrors.value = result
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
            val signInResult: Pair<Int, UserEntity?> = signInUseCase.execute(url, params)

            when (signInResult.first) {
                200 -> {
                    Debugger.Companion.printInfo("Response code - 200. Success")
                    signInResult.second?.let { signInSuccess.value = it }
                }
                450 -> {
                    Debugger.Companion.printInfo("Response code - 450. Wrong credentials")
                    signInErrors.value = listOf(SignInStates.WRONG_CREDENTIALS_FORMAT)
                }
                451 -> {
                    Debugger.Companion.printInfo("Response code - 451 - Wrong email or password")
                    signInErrors.value = listOf(SignInStates.WRONG_EMAIL_OR_PASSWORD)
                }
                else -> {
                    Debugger.Companion.printInfo("Response code in sign in - ${signInResult.first} (unhandled)")
                    signInErrors.value = listOf(SignInStates.NO_SERVER_CONNECTION)
                }
            }
        }
    }

    private fun signInOffline() {
        signInSuccess.value = signInUseCase.getEmptyUser() as UserEntity
    }

    private fun getSignInHandler(): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, throwable ->
            if (throwable::class == ConnectException::class) {
                this.signInErrors.value = listOf(SignInStates.NO_SERVER_CONNECTION)
            }
        }
    }
}