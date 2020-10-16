package com.mctech.stocktradetracking.domain.platform

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineScope {
  fun io(): CoroutineDispatcher
  fun main(): CoroutineDispatcher
  fun default(): CoroutineDispatcher
  fun unconfined(): CoroutineDispatcher
}