package com.mctech.stocktradetracking.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

@ExperimentalCoroutinesApi
open class BaseCoroutineTest {

  @get:Rule
  val coroutinesTestRule =
    CoroutinesMainTestRule()

}