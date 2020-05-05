package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.library.logger.Logger
import com.mctech.stocktradetracking.domain.stock_share.service.StockShareService
import com.mctech.stocktradetracking.testing.data_factory.factories.StockShareDataFactory
import com.mctech.stocktradetracking.testing.data_factory.testScenario
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import java.util.*

@ExperimentalCoroutinesApi
class SellStockShareCaseTest {
    private val service         = mock<StockShareService>()
    private val logger          = mock<Logger>()

    private val expectedError   = RuntimeException()
    private val expectedSingle  = StockShareDataFactory.single()
    private val requestPrice    = 10.0
    private val requestDate     = Calendar.getInstance().time

    private lateinit var useCase: SellStockShareCase

    @Before
    fun `before each test`() {
        useCase = SellStockShareCase(service, logger)
    }

    @Test
    fun `should delegate calling`() = testScenario(
        action = {
            useCase.execute(expectedSingle, requestPrice, requestDate)
        },
        assertions = {
            verify(service).sellStockShare(expectedSingle)
            verifyNoMoreInteractions(service)
        }
    )

    @Test
    fun `should change stock sell values`() = testScenario(
        action = {
            useCase.execute(expectedSingle, requestPrice, requestDate)
        },
        assertions = {
            Assertions.assertThat(expectedSingle.salePrice).isEqualTo(requestPrice)
            Assertions.assertThat(expectedSingle.saleDate).isEqualTo(requestDate)
        }
    )

    @Test
    fun `should log error`() = testScenario(
        scenario = {
            whenever(service.sellStockShare(any())).thenThrow(expectedError)
        },
        action = {
            useCase.execute(expectedSingle, requestPrice, requestDate)
        },
        assertions = {
            verify(logger).e(e = expectedError)
        }
    )
}