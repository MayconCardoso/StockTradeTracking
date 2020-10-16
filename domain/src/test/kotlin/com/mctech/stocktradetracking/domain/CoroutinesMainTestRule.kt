package com.mctech.stocktradetracking.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.ExternalResource

@ExperimentalCoroutinesApi
class CoroutinesMainTestRule : ExternalResource() {

  private val singleThread = newSingleThreadContext("Testing thread")
  val scope = CoroutineScope(singleThread)

  override fun before() {
    Dispatchers.setMain(singleThread)
    super.before()
  }

  override fun after() {
    Dispatchers.resetMain()
    singleThread.close()
    scope.cancel()
    super.after()
  }
}