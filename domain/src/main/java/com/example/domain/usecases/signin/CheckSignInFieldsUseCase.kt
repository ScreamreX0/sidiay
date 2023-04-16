package com.example.domain.usecases.signin

import com.example.core.utils.ApplicationModes.*
import com.example.core.utils.ConstAndVars
import com.example.domain.enums.states.SignInStates
import com.example.domain.data_classes.params.Credentials
import javax.inject.Inject

class CheckSignInFieldsUseCase @Inject constructor() {
    fun execute(params: Credentials): ArrayList<SignInStates> {
        val errorList: ArrayList<SignInStates> = ArrayList()

        if (ConstAndVars.APPLICATION_MODE == DEBUG_AND_ONLINE) return errorList

        // Email errors
        if (params.email.length < ConstAndVars.MIN_EMAIL_LENGHT
            || params.email.length > ConstAndVars.MAX_EMAIL_LENGHT
        ) {
            errorList.add(SignInStates.SHORT_OR_LONG_EMAIL)
        }

        // Password errors
        if (params.password.length < ConstAndVars.MIN_PASSWORD_LENGHT
            || params.password.length > ConstAndVars.MAX_PASSWORD_LENGHT
        ) {
            errorList.add(SignInStates.SHORT_OR_LONG_PASSWORD)
        }

        return errorList
    }
}