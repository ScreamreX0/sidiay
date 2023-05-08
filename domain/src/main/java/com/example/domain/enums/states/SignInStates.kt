package com.example.domain.enums.states

import com.example.core.utils.Constants

enum class SignInStates(private val title: String) : INetworkState {
    SHORT_OR_LONG_PASSWORD("Длина пароля от ${Constants.MIN_PASSWORD_LENGTH} до ${Constants.MAX_PASSWORD_LENGTH} символов"),
    SHORT_OR_LONG_EMAIL("Длина логина от ${Constants.MIN_EMAIL_LENGTH} до ${Constants.MAX_EMAIL_LENGTH} символов"),
    WRONG_EMAIL_OR_PASSWORD("Неверный логин или пароль"),
    UNKNOWN_EXCEPTION("Ошибка");
    override fun toString() = title
}