package com.mctech.stocktradetracking.domain.stock_share_filter.interaction

import com.mctech.library.logger.Logger
import com.mctech.stocktradetracking.domain.stock_share_filter.service.StockShareFilterService
import com.mctech.stocktradetracking.testing.data_factory.factories.StockShareFilterDataFactory
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
class SaveStockShareFilterCaseTest {
  private val service = mock<StockShareFilterService>()
  private val logger = mock<Logger>()

  private val expectedError = RuntimeException()
  private val expectedSingle = StockShareFilterDataFactory.single()

  private lateinit var useCase: SaveStockShareFilterCase

  @Before
  fun `before each test`() {
    useCase = SaveStockShareFilterCase(service, logger)
  }

  @Test
  fun `should delegate calling`() = testScenario(
    action = {
      useCase.execute(expectedSingle)
    },
    assertions = {
      verify(service).saveFilter(expectedSingle)
      verifyNoMoreInteractions(service)
    }
  )

  @Test
  fun `should log error`() = testScenario(
    scenario = {
      whenever(service.saveFilter(any())).thenThrow(expectedError)
    },
    action = {
      useCase.execute(expectedSingle)
    },
    assertions = {
      verify(logger).e(e = expectedError)
    }
  )
}