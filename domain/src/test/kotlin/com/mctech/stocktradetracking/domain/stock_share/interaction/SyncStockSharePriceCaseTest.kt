package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.library.logger.Logger
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
class SyncStockSharePriceCaseTest{
    private val service         = mock<StockShareService>()
    private val logger          = mock<Logger>()

    private val expectedError   = RuntimeException()

    private lateinit var useCase: SyncStockSharePriceCase

    @Before
    fun `before each test`() {
        useCase = SyncStockSharePriceCase(service, logger)
    }

    @Test
    fun `should delegate calling`() = testScenario(
        action = {
            useCase.execute()
        },
        assertions = {
            verify(service).syncStockSharePrice()
            verifyNoMoreInteractions(service)
        }
    )

    @Test
    fun `should log error`() = testScenario(
        scenario = {
            whenever(service.syncStockSharePrice()).thenThrow(expectedError)
        },
        action = {
            useCase.execute()
        },
        assertions = {
            verify(logger).e(e = expectedError)
        }
    )
}