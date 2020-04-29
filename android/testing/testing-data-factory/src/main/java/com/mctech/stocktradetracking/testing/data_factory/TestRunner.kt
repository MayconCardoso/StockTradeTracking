package com.mctech.stocktradetracking.testing.data_factory

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest

@ExperimentalCoroutinesApi
fun <T> testScenario(
        scenario    : suspend () -> Unit = {},
        action      : suspend () -> T,
        assertions  : suspend (result: T) -> Unit
) = runBlockingTest {
    scenario.invoke()
    assertions.invoke( action.invoke() )
}