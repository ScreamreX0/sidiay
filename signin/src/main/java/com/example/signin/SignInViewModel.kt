package com.example.signin

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.ui.utils.Constants
import com.example.core.ui.utils.Debugger
import com.example.domain.enums.states.SignInStates
import com.example.domain.models.entities.UserEntity
import com.example.domain.models.params.ConnectionParams
import com.example.domain.models.params.Credentials
import com.example.domain.usecases.signin.CheckSignInFieldsUseCase
import com.example.signin.data.ConnectionsDataStore
import com.example.signin.domain.states.ConnectionState
import com.example.signin.domain.usecases.CheckConnectionUseCase
import com.example.signin.domain.usecases.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject
import kotlin.random.Random

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

    val checkConnectionResult = mutableStateOf(Pair(ConnectionState.WAITING, 0))
    suspend fun checkConnection(url: String) {
        if (url.last().toString() != "/") {
            url.plus("/")
        }

        checkConnectionResult.value = if (checkConnectionUseCase.execute(url = url)) {
            // Random need to force update mutable state
            Pair(ConnectionState.ESTABLISHED, Random.nextInt())
        } else {
            Pair(ConnectionState.NOT_ESTABLISHED, Random.nextInt())
        }
    }

    suspend fun saveConnections(connectionsList: List<ConnectionParams>) {
        connectionsDataStore.saveConnections(connectionsList)
    }

    /** SignIn */
    var signInErrors: MutableState<List<SignInStates>> = mutableStateOf(listOf())
    var signInSuccess: MutableState<UserEntity> = mutableStateOf(UserEntity())
    fun signIn(url: String, email: String, password: String) {
        val params = Credentials(email, password)

        Debugger.Companion.printInfo("Check sign in credentials")
        val result = checkFieldsUseCase.execute(params = params)

        if (result.size != 0) {
            this.signInErrors.value = result
            return
        }

        if (Constants.DEBUG_MODE) {
            Debugger.Companion.printInfo("Offline sign in")
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