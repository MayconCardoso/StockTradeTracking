package com.mctech.library.logger.android

import android.util.Log
import com.mctech.library.logger.Logger

object LogcatLogger : Logger {
    override fun v(tag: String, message: String) {
        Log.v(tag, message)
    }

    override fun d(tag: String, message: String) {
        Log.d(tag, message)
    }

    override fun i(tag: String, message: String) {
        Log.i(tag, message)
    }

    override fun w(tag: String, message: String) {
        Log.w(tag, message)
    }

    override fun e(tag: String, message: String) {
        Log.e(tag, message)
    }

    override fun e(tag: String, e: Throwable) {
        Log.e(tag, e.message, e)
    }
}
