package com.example.domain.usecases.signin

import com.example.core.utils.ApplicationModes
import com.example.core.utils.ConstAndVars
import com.example.core.utils.Logger
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.Credentials
import com.example.domain.enums.states.INetworkState
import com.example.domain.enums.states.NetworkState
import com.example.domain.enums.states.SignInStates
import com.example.domain.repositories.IAuthRepository
import com.example.domain.usecases.connections.CheckConnectionUseCase
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: IAuthRepository,
    private val checkConnectionUseCase: CheckConnectionUseCase,
    private val checkSignInFieldsUseCase: CheckSignInFieldsUseCase,
) {
    suspend fun execute(url: String?, credentials: Credentials): Pair<INetworkState?, UserEntity?> {
        when (ConstAndVars.APPLICATION_MODE) {
            ApplicationModes.DEBUG_AND_OFFLINE -> return Pair(null, UserEntity(id = 1))
            ApplicationModes.DEBUG_AND_ONLINE -> {
                credentials.email = ConstAndVars.DEBUG_MODE_EMAIL
                credentials.password = ConstAndVars.DEBUG_MODE_PASSWORD
            }

            ApplicationModes.RELEASE -> {
                checkSignInFieldsUseCase.execute(credentials)?.let { return Pair(it, null) }
            }
        }

        url?.let { itUrl ->
            checkConnectionUseCase.execute(itUrl).let {
                if (it == NetworkState.NO_SERVER_CONNECTION) return Pair(it, null)
            }
        } ?: return Pair(NetworkState.NO_SERVER_CONNECTION, null)

        Logger.Companion.m("Online sign in. IP:${ConstAndVars.URL}")
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