package com.mctech.stocktradetracking.testing.data_factory

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest

@ExperimentalCoroutinesApi
fun <T> testScenario(
    scenario        : suspend () -> Unit = {},
    action          : suspend () -> T,
    assertions      : suspend (result: T) -> Unit
) = runBlockingTest {
    scenario.invoke()
    assertions.invoke(action.invoke())
}

@ExperimentalCoroutinesApi
fun <T> testMockedFlowScenario(
    scenario        : suspend (Flow<T?>) -> Unit,
    observe         : suspend () -> Flow<T>,
    action          : suspend (FlowEmission<T?>) -> Unit,
    assertions      : suspend (result: List<T>) -> Unit
) = runBlockingTest {
    val mockedFlow  = ConflatedBroadcastChannel<T?>()
    val values      = mutableListOf<T>()
    val emitter     = FlowEmission<T?>(mockedFlow)

    // Prepare flow
    scenario.invoke(mockedFlow.asFlow())

    // Bind flow

    // Observe values.
    val job = launch {
        observe().collect {
            it?.run(values::add)
        }
    }

    // Perform action
    action.invoke(emitter)

    // Check flow.
    assertions(values)

    // Finish flow.
    job.cancel()
}


@ExperimentalCoroutinesApi
class FlowEmission<T>(private val flow : ConflatedBroadcastChannel<T?>) {
    fun emit(item: T?) {
        flow.offer(item)
    }
}