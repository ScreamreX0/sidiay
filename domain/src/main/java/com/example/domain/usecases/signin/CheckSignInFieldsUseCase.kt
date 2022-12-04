package com.example.domain.usecases.signin

import com.example.domain.utils.Constants
import com.example.domain.enums.SignInStatuses
import com.example.domain.models.SignInParams
import javax.inject.Inject

class CheckSignInFieldsUseCase @Inject constructor() {
    fun execute(params: SignInParams): ArrayList<SignInStatuses> {
        val errorList: ArrayList<SignInStatuses> = ArrayList()

        // Email errors
        if (params.email.length < Constants.MIN_EMAIL_LENGHT
            || params.email.length > Constants.MAX_EMAIL_LENGHT
        ) {
            errorList.add(SignInStatuses.SHORT_OR_LONG_EMAIL)
        }
        // TODO: add regex check (mutually exclusive)

        // Password errors
        if (params.password.length < Constants.MIN_PASSWORD_LENGHT
            || params.password.length > Constants.MAX_PASSWORD_LENGHT
        ) {
            errorList.add(SignInStatuses.SHORT_OR_LONG_PASSWORD)
        }

        return errorList
    }
}