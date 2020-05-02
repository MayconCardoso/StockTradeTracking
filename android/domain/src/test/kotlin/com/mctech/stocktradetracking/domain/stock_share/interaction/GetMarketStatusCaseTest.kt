package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.library.logger.Logger
import com.mctech.stocktradetracking.domain.assertResultFailure
import com.mctech.stocktradetracking.domain.assertResultSuccess
import com.mctech.stocktradetracking.domain.stock_share.entity.MarketStatus
import com.mctech.stocktradetracking.domain.stock_share.service.StockShareService
import com.mctech.stocktradetracking.testing.data_factory.testScenario
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetMarketStatusCaseTest{
    private val service         = mock<StockShareService>()
    private val exception       = mock<RuntimeException>()
    private val logger          = mock<Logger>()
    private val expectedValue   = MarketStatus("", true)

    private lateinit var useCase: GetMarketStatusCase

    @Before
    fun `before each test`() {
        useCase = GetMarketStatusCase(service, logger)
    }

    @Test
    fun `should delegate calling`() = testScenario(
        scenario = {
            whenever(service.getMarketStatus()).thenReturn(expectedValue)
        },
        action = {
            useCase.execute()
        },
        assertions = {
            verify(service).getMarketStatus()
            verifyNoMoreInteractions(service)
        }
    )

    @Test
    fun `should return status`() = testScenario(
        scenario = {
            whenever(service.getMarketStatus()).thenReturn(expectedValue)
        },
        action = {
            useCase.execute()
        },
        assertions = { result ->
            result.assertResultSuccess(expectedValue)
        }
    )

    @Test
    fun `should return unknown exception`() = testScenario(
        scenario = {
            whenever(service.getMarketStatus()).thenThrow(exception)
        },
        action = {
            useCase.execute()
        },
        assertions = { result ->
            result.assertResultFailure(exception)
            verify(logger).e(e = exception)
        }
    )

}