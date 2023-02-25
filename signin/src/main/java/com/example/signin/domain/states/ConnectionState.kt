package com.example.signin.domain.states

import com.example.core.R

enum class ConnectionState(val nameId: Int?) {
    ESTABLISHED(R.string.connection_established),
    NOT_ESTABLISHED(R.string.connection_not_established),
    WAITING(null);
}