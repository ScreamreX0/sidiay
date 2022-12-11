package com.example.domain.utils

import android.util.Log

class Debugger {
    companion object {
        fun printInfo(text: String, tag: String) {
            Log.println(Log.INFO, tag, text)
        }

        fun printError(text: String, tag: String) {
            Log.println(Log.ERROR, tag, text)
        }

        fun printWARN(text: String, tag: String) {
            Log.println(Log.WARN, tag, text)
        }
    }
}