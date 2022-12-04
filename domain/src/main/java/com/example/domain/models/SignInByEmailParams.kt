package com.example.domain.models

data class SignInParams(val email: String, val password: String) {
    fun createBody(): HashMap<String, Any> {
        val response = HashMap<String, Any>()
        response["email"] = email
        response["password"] = password
        return response
    }
}