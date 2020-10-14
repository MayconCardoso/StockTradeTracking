package com.mctech.library.logger

class MutedLogger : Logger {
  override fun v(tag: String, message: String) = Unit
  override fun d(tag: String, message: String) = Unit
  override fun i(tag: String, message: String) = Unit
  override fun w(tag: String, message: String) = Unit
  override fun e(tag: String, message: String) = Unit
  override fun e(tag: String, e: Throwable) = Unit
}