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
import com.example.domain.usecases.settings.SaveLastAuthorizedUseCase
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class SignInUseCase @Inject constructor(
    private val authRepository: IAuthRepository,
    private val checkConnectionUseCase: CheckConnectionUseCase,
    private val checkSignInFieldsUseCase: CheckSignInFieldsUseCase,
    private val saveLastAuthorizedUseCase: SaveLastAuthorizedUseCase
) {
    suspend fun execute(url: String?, credentials: Credentials): Pair<INetworkState?, UserEntity?> {
        if (Constants.APPLICATION_MODE == ApplicationModes.OFFLINE) return Pair(null, UserEntity(id = 1))

        if (url == null || checkConnectionUseCase.execute(url) == NetworkState.NO_SERVER_CONNECTION) {
            return Pair(NetworkState.NO_SERVER_CONNECTION, null)
        }

        return suspendCoroutine { itContinuation ->
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Logger.m("fcm -> ${task.result}")
                    credentials.fcm = task.result

                    if (Constants.APPLICATION_MODE == ApplicationModes.ONLINE) {
                        credentials.email = Constants.DEBUG_MODE_EMAIL
                        credentials.password = Constants.DEBUG_MODE_PASSWORD
                    }


                    val checkSignInFieldsResult = checkSignInFieldsUseCase.execute(credentials)
                    if (checkSignInFieldsResult != null) {
                        itContinuation.resume(Pair(checkSignInFieldsResult, null))
                    } else {
                        runBlocking {
                            Logger.Companion.m("Online sign in. IP:${Constants.URL}")

                            val result = authRepository.signIn(url, credentials)
                            Logger.m("Sign in http code: ${result.first}")

                            itContinuation.resume(when (result.first) {
                                200 -> {
                                    saveLastAuthorizedUseCase.execute(result.second)
                                    Pair(null, result.second)
                                }
                                401 -> Pair(SignInStates.WRONG_EMAIL_OR_PASSWORD, null)
                                else -> {
                                    Logger.m("Unhandled http code: ${result.first}")
                                    Pair(SignInStates.UNKNOWN_EXCEPTION, null)
                                }
                            })
                        }
                    }
                } else {
                    Logger.m("Error -> ${task.result}")
                }
            }
        }
    }
}