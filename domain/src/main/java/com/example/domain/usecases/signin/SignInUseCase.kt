package com.example.domain.usecases.signin

import com.example.core.utils.Logger
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.Credentials
import com.example.domain.repositories.IAuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: IAuthRepository
) {
    suspend fun execute(url: String?, credentials: Credentials): Pair<String?, UserEntity?> {
        if (url == null) return Pair("Неверный url", null)
        val result = authRepository.signIn(url, credentials)
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