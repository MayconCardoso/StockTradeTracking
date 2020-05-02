package com.mctech.stocktradetracking.domain.timeline_balance.interaction

import com.mctech.library.logger.Logger
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
class CreatePeriodCaseTest{
    private val service         = mock<TimelineBalanceService>()
    private val logger          = mock<Logger>()

    private val expectedError   = RuntimeException()
    private val expectedSingle  = TimelineBalanceFactory.single()
    private val expectedParent  = TimelineBalanceFactory.single( id = 12 )

    private lateinit var useCase: CreatePeriodCase

    @Before
    fun `before each test`() {
        useCase = CreatePeriodCase(service, logger)
    }

    @Test
    fun `should delegate calling`() = testScenario(
        action = {
            useCase.execute(expectedSingle)
        },
        assertions = {
            verify(service).getLastPeriod()
            verify(service).createPeriod(expectedSingle)
            verifyNoMoreInteractions(service)
        }
    )

    @Test
    fun `should create without parent`() = testScenario(
        scenario = {
            whenever(service.getLastPeriod()).thenReturn(null)
        },
        action = {
            useCase.execute(expectedSingle)
        },
        assertions = {
            Assertions.assertThat(expectedSingle.parentPeriodId).isNull()
        }
    )

    @Test
    fun `should create with parent`() = testScenario(
        scenario = {
            whenever(service.getLastPeriod()).thenReturn(expectedParent)
        },
        action = {
            useCase.execute(expectedSingle)
        },
        assertions = {
            Assertions.assertThat(expectedSingle.parentPeriodId).isEqualTo(12)
        }
    )

    @Test
    fun `should log error loading period`() = testScenario(
        scenario = {
            whenever(service.getLastPeriod()).thenThrow(expectedError)
        },
        action = {
            useCase.execute(expectedSingle)
        },
        assertions = {
            verify(logger).e(e = expectedError)
        }
    )

    @Test
    fun `should log error saving`() = testScenario(
        scenario = {
            whenever(service.createPeriod(expectedSingle)).thenThrow(expectedError)
        },
        action = {
            useCase.execute(expectedSingle)
        },
        assertions = {
            verify(logger).e(e = expectedError)
        }
    )
}