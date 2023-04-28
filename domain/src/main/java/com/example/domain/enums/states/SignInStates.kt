package com.example.domain.enums.states

import com.example.core.utils.ConstAndVars

enum class SignInStates(val title: String) {
    SHORT_OR_LONG_PASSWORD("Длина пароля от ${ConstAndVars.MIN_PASSWORD_LENGHT} до ${ConstAndVars.MAX_PASSWORD_LENGHT} символов"),
    SHORT_OR_LONG_EMAIL("Длина логина от ${ConstAndVars.MIN_EMAIL_LENGHT} до ${ConstAndVars.MAX_EMAIL_LENGHT} символов"),
    WRONG_EMAIL_OR_PASSWORD("Неверный логин или пароль"),
    WRONG_CREDENTIALS_FORMAT("Неверный формат логина или пароля"),
    NO_SERVER_CONNECTION("Нет соединения с сервером")
}