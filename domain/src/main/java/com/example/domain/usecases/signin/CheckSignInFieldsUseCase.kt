package com.example.domain.usecases.signin

import com.example.core.utils.ConstAndVars
import com.example.domain.data_classes.params.Credentials
import com.example.domain.enums.states.SignInStates
import javax.inject.Inject

class CheckSignInFieldsUseCase @Inject constructor() {
    fun execute(params: Credentials): SignInStates? {
        if (params.email.length < ConstAndVars.MIN_EMAIL_LENGHT
            || params.email.length > ConstAndVars.MAX_EMAIL_LENGHT
        ) { return SignInStates.SHORT_OR_LONG_EMAIL }

        if (params.password.length < ConstAndVars.MIN_PASSWORD_LENGHT
            || params.password.length > ConstAndVars.MAX_PASSWORD_LENGHT
        ) { return SignInStates.SHORT_OR_LONG_PASSWORD }

        return null
    }
}