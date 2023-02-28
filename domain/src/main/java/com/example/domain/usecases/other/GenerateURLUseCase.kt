package com.example.domain.usecases.other

class GenerateURLUseCase {
    fun create(baseUrl: String, vararg args: Pair<String, Any>): String {
        var newUrl = baseUrl.plus("?")
        for (argument in args) {
            newUrl = newUrl.plus("${argument.first}=${argument.second}&")
        }

        // Remove last '&' sign
        newUrl = newUrl.substring(0, newUrl.lastIndex)
        return newUrl
    }
}