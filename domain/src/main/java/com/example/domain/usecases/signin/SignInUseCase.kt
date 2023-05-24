package com.example.domain.usecases.signin

import com.example.core.utils.ApplicationModes
import com.example.core.utils.Constants
import com.example.core.utils.Logger
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.Credentials
import com.example.domain.enums.states.INetworkState
import com.example.domain.enums.states.NetworkState
import com.example.domain.enums.states.SignInStates
import com.example.domain.repositories.IAuthRepository
import com.example.domain.usecases.connections.CheckConnectionUseCase
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import javax.inject.Inject


class SignInUseCase @Inject constructor(
    private val authRepository: IAuthRepository,
    private val checkConnectionUseCase: CheckConnectionUseCase,
    private val checkSignInFieldsUseCase: CheckSignInFieldsUseCase,
) {
    suspend fun execute(url: String?, credentials: Credentials): Pair<INetworkState?, UserEntity?> {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Logger.m("Fetching fcm failed")
                return@OnCompleteListener
            }
            Logger.m("fsm -> ${task.result}")
        })

        when (Constants.APPLICATION_MODE) {
            ApplicationModes.OFFLINE -> {
                return Pair(null, UserEntity(id = 1))
            }

            ApplicationModes.ONLINE -> {
                credentials.email = Constants.DEBUG_MODE_EMAIL
                credentials.password = Constants.DEBUG_MODE_PASSWORD
            }

            ApplicationModes.RELEASE -> {
                checkSignInFieldsUseCase.execute(credentials)?.let { return Pair(it, null) }
            }
        }

        if (url == null || checkConnectionUseCase.execute(url) == NetworkState.NO_SERVER_CONNECTION) {
            return Pair(NetworkState.NO_SERVER_CONNECTION, null)
        }

        Logger.Companion.m("Online sign in. IP:${Constants.URL}")
        val result = authRepository.signIn(url, credentials)
        Logger.m("Sign in http code: ${result.first}")

        return when (result.first) {
            200 -> Pair(null, result.second)
            401 -> Pair(SignInStates.WRONG_EMAIL_OR_PASSWORD, null)
            else -> {
                Logger.m("Unhandled http code: ${result.first}")
                Pair(SignInStates.UNKNOWN_EXCEPTION, null)
            }
        }
    }
}