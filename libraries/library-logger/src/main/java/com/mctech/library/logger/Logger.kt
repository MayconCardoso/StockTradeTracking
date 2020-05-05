package com.mctech.library.logger

interface Logger {
    companion object{
        const val DEFAULT_TAG = "StockTradeTracking"
    }

    fun v(tag : String = DEFAULT_TAG, message: String)
    fun d(tag : String = DEFAULT_TAG, message: String)
    fun i(tag : String = DEFAULT_TAG, message: String)
    fun w(tag : String = DEFAULT_TAG, message: String)
    fun e(tag : String = DEFAULT_TAG, message: String)
    fun e(tag : String = DEFAULT_TAG, e: Throwable)
}