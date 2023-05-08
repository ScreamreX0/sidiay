package com.example.domain.usecases.signin

import com.example.core.utils.Constants
import com.example.domain.data_classes.params.Credentials
import com.example.domain.enums.states.SignInStates
import javax.inject.Inject

class CheckSignInFieldsUseCase @Inject constructor() {
    fun execute(params: Credentials): SignInStates? {
        if (params.email.length < Constants.MIN_EMAIL_LENGTH
            || params.email.length > Constants.MAX_EMAIL_LENGTH
        ) { return SignInStates.SHORT_OR_LONG_EMAIL }

        if (params.password.length < Constants.MIN_PASSWORD_LENGTH
            || params.password.length > Constants.MAX_PASSWORD_LENGTH
        ) { return SignInStates.SHORT_OR_LONG_PASSWORD }

        return null
    }
}