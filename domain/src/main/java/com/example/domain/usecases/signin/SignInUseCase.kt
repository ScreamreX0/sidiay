package com.example.domain.usecases.signin

import com.example.core.utils.ApplicationModes
import com.example.core.utils.ConstAndVars
import com.example.core.utils.Logger
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.Credentials
import com.example.domain.enums.states.NetworkConnectionState
import com.example.domain.repositories.IAuthRepository
import com.example.domain.usecases.connections.CheckConnectionUseCase
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: IAuthRepository,
    private val checkConnectionUseCase: CheckConnectionUseCase,
    private val checkSignInFieldsUseCase: CheckSignInFieldsUseCase,
) {
    suspend fun execute(url: String?, credentials: Credentials): Pair<String?, UserEntity?> {
        if (ConstAndVars.APPLICATION_MODE == ApplicationModes.DEBUG_AND_OFFLINE) return Pair(null, UserEntity(id = 1))

        if (ConstAndVars.APPLICATION_MODE == ApplicationModes.DEBUG_AND_ONLINE) {
            credentials.email = ConstAndVars.DEBUG_MODE_EMAIL
            credentials.password = ConstAndVars.DEBUG_MODE_PASSWORD
        } else {
            checkSignInFieldsUseCase.execute(credentials)?.let { return Pair(it, null) }
        }

        Logger.Companion.m("Online sign in. IP:${ConstAndVars.URL}")
        checkConnectionUseCase.execute(url).let {
            if (it == NetworkConnectionState.NO_SERVER_CONNECTION || it == NetworkConnectionState.NO_NETWORK_CONNECTION) {
                return Pair(it.title, null)
            }
        }

        val result = authRepository.signIn(url!!, credentials)
        Logger.m("Sign in http code: ${result.first}")

        return when(result.first) {
            200 -> Pair(null, result.second)
            401 -> Pair("Неверный логин или пароль", null)
            else -> {
                Logger.m("Unhandled http code: ${result.first}")
                Pair("Неизвестная ошибка", null)
            }
        }
    }
}