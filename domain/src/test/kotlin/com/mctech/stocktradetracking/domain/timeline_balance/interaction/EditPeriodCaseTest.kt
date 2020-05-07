package com.mctech.stocktradetracking.domain.timeline_balance.interaction

import com.mctech.library.logger.Logger
import com.mctech.stocktradetracking.domain.timeline_balance.service.TimelineBalanceService
import com.mctech.stocktradetracking.testing.data_factory.factories.TimelineBalanceFactory
import com.mctech.stocktradetracking.testing.data_factory.testScenario
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class EditPeriodCaseTest {
    private val service         = mock<TimelineBalanceService>()
    private val logger          = mock<Logger>()
    private val expectedError   = RuntimeException()
    private val originalObject  = TimelineBalanceFactory.single(
        periodInvestment = 10000.0,
        periodProfit = 1000.0
    )

    private val changedChangingProfit  = TimelineBalanceFactory.single(
        periodInvestment = 12000.0,
        periodProfit = 1100.0
    )

    private lateinit var useCase: EditPeriodCase

    @Before
    fun `before each test`() {
        useCase = EditPeriodCase(service, logger)
    }

    @Test
    fun `should delegate calling`() = testScenario(
        action = {
            useCase.execute(originalObject, changedChangingProfit, 0.0)
        },
        assertions = {
            verify(service).editPeriod(originalObject)
            verifyNoMoreInteractions(service)
        }
    )

    @Test
    fun `should change period profit`() = testScenario(
        scenario = {
            assertThat(originalObject.getFinalBalance()).isEqualTo(11000.0)
        },
        action = {
            useCase.execute(originalObject, changedChangingProfit, 11000.0)
        },
        assertions = {
            assertThat(originalObject.periodInvestment).isEqualTo(changedChangingProfit.periodInvestment)
            assertThat(originalObject.periodProfit).isEqualTo(changedChangingProfit.periodProfit)
            assertThat(originalObject.getFinalBalance()).isEqualTo(13100.0)
            verify(service).editPeriod(originalObject)
            verifyNoMoreInteractions(service)
        }
    )

    @Test
    fun `should change final period balance`() = testScenario(
        scenario = {
            assertThat(originalObject.periodInvestment).isNotEqualTo(changedChangingProfit.periodInvestment)
        },
        action = {
            useCase.execute(originalObject, changedChangingProfit, 25000.0)
        },
        assertions = {
            assertThat(originalObject.periodInvestment).isEqualTo(changedChangingProfit.periodInvestment)
            assertThat(originalObject.periodProfit).isEqualTo(13000.0)
            assertThat(originalObject.getFinalBalance()).isEqualTo(25000.0)
            verify(service).editPeriod(originalObject)
            verifyNoMoreInteractions(service)
        }
    )

    @Test
    fun `should change period investment balance`() = testScenario(
        scenario = {
            assertThat(originalObject.periodInvestment).isNotEqualTo(changedChangingProfit.periodInvestment)
        },
        action = {
            useCase.execute(originalObject, changedChangingProfit, 25000.0)
        },
        assertions = {
            assertThat(originalObject.periodInvestment).isEqualTo(changedChangingProfit.periodInvestment)
            verify(service).editPeriod(originalObject)
            verifyNoMoreInteractions(service)
        }
    )

    @Test
    fun `should log error`() = testScenario(
        scenario = {
            whenever(service.editPeriod(any())).thenThrow(expectedError)
        },
        action = {
            useCase.execute(originalObject, changedChangingProfit, 0.0)
        },
        assertions = {
            verify(logger).e(e = expectedError)
        }
    )
}