package com.example.signin

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.ui.utils.Constants
import com.example.core.ui.utils.Debugger
import com.example.domain.enums.states.SignInStates
import com.example.domain.models.entities.UserEntity
import com.example.domain.models.params.ConnectionParams
import com.example.domain.models.params.Credentials
import com.example.domain.usecases.signin.CheckSignInFieldsUseCase
import com.example.domain.usecases.signin.SignInUseCase
import com.example.signin.data.ConnectionsDataStore
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
) : ViewModel() {
    var errorResult = MutableLiveData<List<SignInStates>>()
    var successSignIn = MutableLiveData<UserEntity>()

    fun signIn(email: String, password: String) {
        val params = Credentials(email, password)

        Debugger.Companion.printInfo("Check sign in credentials")
        val result = checkFieldsUseCase.execute(params = params)

        if (result.size != 0) {
            this.errorResult.value = result
            return
        }

        if (Constants.DEBUG_MODE) {
            Debugger.Companion.printInfo("Offline sign in")
            signInOffline()
        } else {
            Debugger.Companion.printInfo("Online sign in. IP:${Constants.URL}")
            signInOnline(params = params)
        }
    }

    suspend fun saveConnections(connectionsList: List<ConnectionParams>) {
        connectionsDataStore.saveConnections(connectionsList)
    }

    var connectionsList = mutableStateOf<List<ConnectionParams>>(listOf())

    init {
        viewModelScope.launch {
            connectionsList = mutableStateOf(
                connectionsDataStore.getConnections.first() as List<ConnectionParams>
            )
        }
    }

    private fun signInOnline(params: Credentials) {
        viewModelScope.launch(getSignInHandler()) {
            val signInResult: Pair<Int, UserEntity?> = signInUseCase.execute(params)

            when (signInResult.first) {
                200 -> {
                    Debugger.Companion.printInfo("Response code - 200. Success")
                    signInResult.let {
                        signInResult.second.let {
                            successSignIn.value = it
                        }
                    }
                }
                450 -> {
                    Debugger.Companion.printInfo("Response code - 450. Wrong credentials")
                    errorResult.value = listOf(SignInStates.WRONG_CREDENTIALS_FORMAT)
                }
                451 -> {
                    Debugger.Companion.printInfo("Response code - 451 - Wrong email or password")
                    errorResult.value = listOf(SignInStates.WRONG_EMAIL_OR_PASSWORD)
                }
                else -> {
                    Debugger.Companion.printInfo("Response code in sign in - unhandled")
                    errorResult.value = listOf(SignInStates.NO_SERVER_CONNECTION)
                }
            }
        }
    }

    private fun signInOffline() {
        successSignIn.value = signInUseCase.getEmptyUser() as UserEntity
    }

    private fun getSignInHandler(): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, throwable ->
            if (throwable::class == ConnectException::class) {
                this.errorResult.value = listOf(SignInStates.NO_SERVER_CONNECTION)
            }
        }
    }
}