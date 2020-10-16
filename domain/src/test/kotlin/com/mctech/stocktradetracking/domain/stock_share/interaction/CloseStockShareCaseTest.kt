package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.library.logger.Logger
import com.mctech.stocktradetracking.domain.stock_share.service.StockShareService
import com.mctech.stocktradetracking.testing.data_factory.factories.StockShareDataFactory
import com.mctech.stocktradetracking.testing.data_factory.testScenario
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CloseStockShareCaseTest {
  private val service = mock<StockShareService>()
  private val logger = mock<Logger>()

  private val expectedError = RuntimeException()
  private val expectedSingle = StockShareDataFactory.single()

  private lateinit var useCase: CloseStockShareCase

  @Before
  fun `before each test`() {
    useCase = CloseStockShareCase(service, logger)
  }

  @Test
  fun `should delegate calling`() = testScenario(
    action = {
      useCase.execute(expectedSingle)
    },
    assertions = {
      verify(service).closeStockShare(expectedSingle)
      verifyNoMoreInteractions(service)
    }
  )

  @Test
  fun `should log error`() = testScenario(
    scenario = {
      whenever(service.closeStockShare(any())).thenThrow(expectedError)
    },
    action = {
      useCase.execute(expectedSingle)
    },
    assertions = {
      verify(logger).e(e = expectedError)
    }
  )
}