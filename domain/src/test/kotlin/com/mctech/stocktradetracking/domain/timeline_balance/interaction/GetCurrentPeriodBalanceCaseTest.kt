package com.mctech.stocktradetracking.domain.timeline_balance.interaction

import com.mctech.library.logger.Logger
import com.mctech.stocktradetracking.domain.Result
import com.mctech.stocktradetracking.domain.assertResultFailure
import com.mctech.stocktradetracking.domain.assertResultSuccess
import com.mctech.stocktradetracking.domain.timeline_balance.TimelineBalanceError
import com.mctech.stocktradetracking.domain.timeline_balance.service.TimelineBalanceService
import com.mctech.stocktradetracking.testing.data_factory.factories.TimelineBalanceFactory
import com.mctech.stocktradetracking.testing.data_factory.testScenario
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetCurrentPeriodBalanceCaseTest{
    private val service         = mock<TimelineBalanceService>()
    private val logger          = mock<Logger>()

    private val expectedLinked  = TimelineBalanceFactory.linkedEntitiesWithId()

    private lateinit var useCase: GetCurrentPeriodBalanceCase

    @Before
    fun `before each test`() {
        useCase = GetCurrentPeriodBalanceCase(service, logger)
    }

    @Test
    fun `should delegate calling`() = testScenario(
        scenario = {
            whenever(service.getListOfPeriodsBalance()).thenReturn(expectedLinked)
        },
        action = {
            useCase.execute()
        },
        assertions = {
            verify(service).getListOfPeriodsBalance()
            verifyNoMoreInteractions(service)
        }
    )

    @Test
    fun `should return list`() = testScenario(
        scenario = {
            whenever(service.getListOfPeriodsBalance()).thenReturn(expectedLinked)
        },
        action = {
            useCase.execute()
        },
        assertions = { result ->
            result.assertResultSuccess(expectedLinked)
            verify(service).getListOfPeriodsBalance()
            verifyNoMoreInteractions(service)
        }
    )

    @Test
    fun `should return link parent`() = testScenario(
        scenario = {
            whenever(service.getListOfPeriodsBalance()).thenReturn(expectedLinked)
        },
        action = {
            useCase.execute()
        },
        assertions = { result ->
            result.assertResultSuccess(expectedLinked)

            val response = (result as Result.Success).result
            Assertions.assertThat(response[0].parent).isNull()
            Assertions.assertThat(response[1].parent).isNotNull
            Assertions.assertThat(response[1].parent?.id).isEqualTo(1)
            Assertions.assertThat(response[1].parent?.periodTag).isEqualTo("First")
        }
    )

    @Test
    fun `should return known exception`() = failureAssertion(
        exception = TimelineBalanceError.UnknownExceptionException,
        expectedException = TimelineBalanceError.UnknownExceptionException
    )

    @Test
    fun `should return unknown exception`() = failureAssertion(
        exception = RuntimeException(),
        expectedException = TimelineBalanceError.UnknownExceptionException
    )

    private fun failureAssertion(exception: Throwable, expectedException: Exception) = testScenario(
        scenario = {
            whenever(service.getListOfPeriodsBalance()).thenThrow(exception)
        },
        action = {
            useCase.execute()
        },
        assertions = { result ->
            result.assertResultFailure(expectedException)
            verify(logger).e(e = exception)
        }
    )
}