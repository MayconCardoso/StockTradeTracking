package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.library.logger.Logger
import com.mctech.stocktradetracking.domain.assertResultFailure
import com.mctech.stocktradetracking.domain.assertResultSuccess
import com.mctech.stocktradetracking.domain.stock_share.StockShareError
import com.mctech.stocktradetracking.domain.stock_share.service.StockShareService
import com.mctech.stocktradetracking.testing.data_factory.factories.StockShareDataFactory
import com.mctech.stocktradetracking.testing.data_factory.testScenario
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetStockShareListCaseTest {
    private val service         = mock<StockShareService>()
    private val logger          = mock<Logger>()

    private val expectedValue   = StockShareDataFactory.listOf(10)

    private lateinit var useCase: GetStockShareListCase

    @Before
    fun `before each test`() {
        useCase = GetStockShareListCase(service, logger)
    }
    
    @Test
    fun `should delegate calling`() = testScenario(
        scenario = {
            whenever(service.getStockShareList()).thenReturn(expectedValue)
        },
        action = {
            useCase.execute()
        },
        assertions = {
            verify(service).getStockShareList()
        }
    )

    @Test
    fun `should return list`() = testScenario(
        scenario = {
            whenever(service.getStockShareList()).thenReturn(expectedValue)
        },
        action = {
            useCase.execute()
        },
        assertions = { result ->
            result.assertResultSuccess(expectedValue)
        }
    )

    @Test
    fun `should return known exception`() = failureAssertion(
        exception = StockShareError.InvalidSharePriceException,
        expectedException = StockShareError.InvalidSharePriceException
    )

    @Test
    fun `should return not found exception`() = failureAssertion(
        exception = StockShareError.ShareCodeNotFoundException,
        expectedException = StockShareError.ShareCodeNotFoundException
    )

    @Test
    fun `should return unknown exception`() = failureAssertion(
        exception = RuntimeException(),
        expectedException = StockShareError.UnknownExceptionException
    )

    private fun failureAssertion(exception: Throwable, expectedException: Exception) = testScenario(
        scenario = {
            whenever(service.getStockShareList()).thenThrow(exception)
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