package com.example.domain.enums

enum class SignUpStatuses {
    // Username
    SHORT_OR_LONG_USERNAME,

    // Email
    SHORT_OR_LONG_EMAIL,
    INCORRECT_EMAIL,  // regex check

    // Password
    SHORT_OR_LONG_PASSWORD,
    PASSWORDS_DO_NOT_MATCH,

    // Common
    WRONG_EMAIL_OR_PASSWORD,
    NO_SERVER_CONNECTION
}