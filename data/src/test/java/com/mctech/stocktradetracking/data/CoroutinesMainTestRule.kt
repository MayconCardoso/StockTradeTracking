package com.mctech.stocktradetracking.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class CoroutinesMainTestRule(
  val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : InstantTaskExecutorRule() {

  override fun starting(description: Description?) {
    super.starting(description)
    Dispatchers.setMain(testDispatcher)
  }

  override fun finished(description: Description?) {
    super.finished(description)
    Dispatchers.resetMain()
    testDispatcher.cleanupTestCoroutines()
  }
}