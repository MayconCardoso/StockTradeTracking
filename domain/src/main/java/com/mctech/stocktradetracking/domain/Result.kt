package com.mctech.stocktradetracking.domain

sealed class Result<out T> {
  data class Success<T>(val result: T) : Result<T>()
  data class Failure(val throwable: Throwable) : Result<Nothing>()
}
