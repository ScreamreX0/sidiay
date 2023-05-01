package com.example.domain.enums.states

import com.example.core.utils.ConstAndVars

enum class SignInStates(private val title: String) : INetworkState {
    SHORT_OR_LONG_PASSWORD("Длина пароля от ${ConstAndVars.MIN_PASSWORD_LENGHT} до ${ConstAndVars.MAX_PASSWORD_LENGHT} символов"),
    SHORT_OR_LONG_EMAIL("Длина логина от ${ConstAndVars.MIN_EMAIL_LENGHT} до ${ConstAndVars.MAX_EMAIL_LENGHT} символов"),
    WRONG_EMAIL_OR_PASSWORD("Неверный логин или пароль"),
    UNKNOWN_EXCEPTION("Ошибка");
    override fun toString() = title
}