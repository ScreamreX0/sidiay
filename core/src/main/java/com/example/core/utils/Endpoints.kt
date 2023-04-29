package com.example.core.utils

object Endpoints {
    object Users {
        private const val USERS = "/users"
        const val SIGN_IN = "$USERS/sign-in"
    }

    object Tickets {
        private const val TICKETS = "/tickets"
        const val GET_DATA = "$TICKETS/get-data"
        const val GET_BY_ID = "$TICKETS/get-by-id"
        const val ADD = "$TICKETS/add"
        const val UPDATE = "$TICKETS/update"
    }
}