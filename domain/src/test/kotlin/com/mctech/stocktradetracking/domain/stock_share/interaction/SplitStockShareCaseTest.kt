package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.library.logger.Logger
import com.mctech.stocktradetracking.domain.stock_share.service.StockShareService
import com.mctech.stocktradetracking.testing.data_factory.factories.StockShareDataFactory
import com.mctech.stocktradetracking.testing.data_factory.testScenario
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class SplitStockShareCaseTest {
  private lateinit var useCase: SplitStockShareCase

  private val service = mock<StockShareService>()
  private val logger = mock<Logger>()

  private val expectedItems = StockShareDataFactory.splitList()
  private val expectedError = RuntimeException()
  private val stockInput = StockShareDataFactory.single(code = "MGLU3")


  @Before
  fun `before each test`() {
    useCase = SplitStockShareCase(service, logger)
  }

  @Test
  fun `should log error while loading`() = testScenario(
    scenario = {
      whenever(service.getAllByCode(any())).thenThrow(expectedError)
    },
    action = {
      useCase.execute(share = stockInput, splitRatio = 4)
    },
    assertions = {
      verify(logger).e(e = expectedError)
    }
  )

  @Test
  fun `should log error while saving`() = testScenario(
    scenario = {
      whenever(service.saveStockShare(any())).thenThrow(expectedError)
      whenever(service.getAllByCode(any())).thenReturn(StockShareDataFactory.listOf(3))
    },
    action = {
      useCase.execute(share = stockInput, splitRatio = 4)
    },
    assertions = {
      verify(logger).e(e = expectedError)
    }
  )


  @Test
  fun `should update items`() = testScenario(
    scenario = {
      whenever(service.getAllByCode(any())).thenReturn(expectedItems)
    },
    action = {
      useCase.execute(share = stockInput, splitRatio = 4)
    },
    assertions = {
      verify(service).getAllByCode("MGLU3")
      verify(service, times(2)).saveStockShare(any())

      // Load fresh list again
      val validationList = StockShareDataFactory.splitList()

      // Assert first item
      val firstExpected = expectedItems[0]
      val firstFresh = validationList[0]
      assertThat(firstExpected.shareAmount).isEqualTo(firstFresh.shareAmount * 4)
      assertThat(firstExpected.purchasePrice).isEqualTo(
        firstFresh.getInvestmentValue() / firstExpected.shareAmount
      )

      // Assert second item
      val secondExpected = expectedItems[1]
      val secondFresh = validationList[1]
      assertThat(secondExpected.shareAmount).isEqualTo(secondFresh.shareAmount * 4)
      assertThat(secondExpected.purchasePrice).isEqualTo(
        secondFresh.getInvestmentValue() / secondExpected.shareAmount
      )
    }
  )
}