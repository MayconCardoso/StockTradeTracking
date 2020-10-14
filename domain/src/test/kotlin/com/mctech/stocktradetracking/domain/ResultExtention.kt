package com.mctech.stocktradetracking.domain

import org.assertj.core.api.Assertions

fun Result<*>.assertResultFailure(expectedException: Exception) {
  val resultException = (this as Result.Failure).throwable
  Assertions.assertThat(this).isInstanceOf(Result.Failure::class.java)
  Assertions.assertThat(resultException).isEqualTo(expectedException)
}

fun <T> Result<T>.assertResultSuccess(expectedValue: T) {
  val expectedResult =
    Result.Success(
      expectedValue
    )
  val entity = (this as Result.Success).result

  Assertions.assertThat(this)
    .isExactlyInstanceOf(Result.Success::class.java)
    .isEqualTo(expectedResult)

  Assertions.assertThat(entity).isEqualTo(expectedValue)
}