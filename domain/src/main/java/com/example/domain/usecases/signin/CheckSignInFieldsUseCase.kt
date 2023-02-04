package com.example.domain.usecases.signin

import com.example.domain.utils.Constants
import com.example.domain.enums.states.SignInStates
import com.example.domain.models.params.Credentials
import javax.inject.Inject

class CheckSignInFieldsUseCase @Inject constructor() {
    fun execute(params: Credentials): ArrayList<SignInStates> {
        val errorList: ArrayList<SignInStates> = ArrayList()

        // Email errors
        if (params.email.length < Constants.MIN_EMAIL_LENGHT
            || params.email.length > Constants.MAX_EMAIL_LENGHT
        ) {
            errorList.add(SignInStates.SHORT_OR_LONG_EMAIL)
        }

        // Password errors
        if (params.password.length < Constants.MIN_PASSWORD_LENGHT
            || params.password.length > Constants.MAX_PASSWORD_LENGHT
        ) {
            errorList.add(SignInStates.SHORT_OR_LONG_PASSWORD)
        }

        return errorList
    }
}