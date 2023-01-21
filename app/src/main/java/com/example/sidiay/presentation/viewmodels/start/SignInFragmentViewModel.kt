package com.example.sidiay.presentation.viewmodels.start

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.entities.User
import com.example.domain.enums.states.SignInStates
import com.example.domain.models.params.Credentials
import com.example.domain.usecases.signin.CheckSignInFieldsUseCase
import com.example.domain.usecases.signin.SignInUseCase
import com.example.domain.utils.Constants
import com.example.domain.utils.Debugger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class SignInFragmentViewModel @Inject constructor(
    private val checkFieldsUseCase: CheckSignInFieldsUseCase,
    private val signInUseCase: SignInUseCase
) : ViewModel() {
    var errorResult = MutableLiveData<List<SignInStates>>()
    var successSignIn = MutableLiveData<User>()

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

    private fun signInOnline(params: Credentials) {
        viewModelScope.launch(getSignInHandler()) {
            val signInResult: Pair<Int, User?> = signInUseCase.execute(params)

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
                    throw java.lang.Exception("Unhandled response code exception")
                }
            }
        }
    }

    private fun signInOffline() {
        successSignIn.value = signInUseCase.getEmptyUser() as User
    }

    private fun getSignInHandler(): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, throwable ->
            if (throwable::class == ConnectException::class) {
                this.errorResult.value = listOf(SignInStates.NO_SERVER_CONNECTION)
            }
        }
    }
}