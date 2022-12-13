package com.example.sidiay.presentation.viewmodels.start

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.entities.User
import com.example.domain.enums.SignInStatuses
import com.example.domain.models.SignInParams
import com.example.domain.usecases.signin.CheckSignInFieldsUseCase
import com.example.domain.usecases.signin.SignInUseCase
import com.example.domain.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class SignInFragmentViewModel @Inject constructor(
    private val checkFieldsUseCase: CheckSignInFieldsUseCase,
    private val authUseCase: SignInUseCase
) : ViewModel() {
    var errorResult = MutableLiveData<List<SignInStatuses>>()
    var successAuth = MutableLiveData<User>()

    fun signIn(email: String, password: String) {
        val params = SignInParams(email, password)

        val result = checkFieldsUseCase.execute(params = params)

        if (result.size != 0) {
            this.errorResult.value = result
            return
        }

        if (Constants.DEBUG_MODE) {
            signInOffline()
        } else {
            signInOnline(params = params)
        }
    }

    private fun signInOnline(params: SignInParams) {
        viewModelScope.launch(getAuthHandler()) {
            val authResult = authUseCase.execute(params = params)

            if (authResult == null) {
                errorResult.value = listOf(SignInStatuses.WRONG_EMAIL_OR_PASSWORD)
            } else {
                successAuth.value = authResult!!
            }
        }
    }

    private fun signInOffline() {
        successAuth.value = authUseCase.getEmptyUser() as User
    }

    private fun getAuthHandler(): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, throwable ->
            if (throwable::class == ConnectException::class) {
                this.errorResult.value = listOf(SignInStatuses.NO_SERVER_CONNECTION)
            }
        }
    }
}