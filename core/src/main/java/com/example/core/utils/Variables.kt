package com.example.core.utils

object Variables {
    val DEFAULT_CONNECTION_URL = if (Constants.DEBUG_MODE) Constants.LOCAL_URL else Constants.URL
}