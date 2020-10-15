package com.mctech.stocktradetracking.data.stock_share.repository

import com.mctech.library.logger.Logger
import com.mctech.stocktradetracking.data.stock_share.api.StockPriceResponse
import com.mctech.stocktradetracking.data.stock_share.datasource.LocalStockShareDataSource
import com.mctech.stocktradetracking.data.stock_share.datasource.RemoteStockShareDataSource
import com.mctech.stocktradetracking.domain.stock_share.entity.MarketStatus
import com.mctech.stocktradetracking.testing.data_factory.factories.StockShareDataFactory
import com.mctech.stocktradetracking.testing.data_factory.testScenario
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class StockShareRepositoryTest {
  private val localDataSource = mock<LocalStockShareDataSource>()
  private val remoteDataSource = mock<RemoteStockShareDataSource>()
  private val logger = mock<Logger>()
  private val expectedSingle = StockShareDataFactory.single()
  private val expectedAll = StockShareDataFactory.listOf()
  private val expectedMarket = MarketStatus("", true)
  private val expectedError = RuntimeException()

  private lateinit var repository: StockShareRepository

  @Before
  fun `before each test`() {
    repository = StockShareRepository(logger, localDataSource, remoteDataSource)
  }

  @Test
  fun `should observe opened stock share list`() = testScenario(
    action = {
      repository.observeStockShareList()
    },
    assertions = {
      verify(localDataSource).observeStockShareList()
      verifyNoMoreInteractions(localDataSource)
      verifyZeroInteractions(remoteDataSource)
    }
  )

  @Test
  fun `should observe closed stock share list`() = testScenario(
    action = {
      repository.observeStockClosedList()
    },
    assertions = {
      verify(localDataSource).observeStockClosedList()
      verifyNoMoreInteractions(localDataSource)
      verifyZeroInteractions(remoteDataSource)
    }
  )

  @Test
  fun `should save stock share list`() = testScenario(
    action = {
      repository.saveStockShare(expectedSingle)
    },
    assertions = {
      verify(localDataSource).saveStockShare(expectedSingle)
      verifyNoMoreInteractions(localDataSource)
      verifyZeroInteractions(remoteDataSource)
    }
  )

  @Test
  fun `should sell stock share list`() = testScenario(
    action = {
      repository.sellStockShare(expectedSingle)
    },
    assertions = {
      verify(localDataSource).sellStockShare(expectedSingle)
      verifyNoMoreInteractions(localDataSource)
      verifyZeroInteractions(remoteDataSource)
    }
  )

  @Test
  fun `should delete stock share list`() = testScenario(
    action = {
      repository.deleteStockShare(expectedSingle)
    },
    assertions = {
      verify(localDataSource).deleteStockShare(expectedSingle)
      verifyNoMoreInteractions(localDataSource)
      verifyZeroInteractions(remoteDataSource)
    }
  )

  @Test
  fun `should close stock share list`() = testScenario(
    action = {
      repository.closeStockShare(expectedSingle)
    },
    assertions = {
      verify(localDataSource).closeStockShare(expectedSingle)
      verifyNoMoreInteractions(localDataSource)
      verifyZeroInteractions(remoteDataSource)
    }
  )


  @Test
  fun `should return market close`() = testScenario(
    scenario = {
      whenever(localDataSource.getMarketStatus()).thenReturn(expectedMarket)
    },
    action = {
      repository.getMarketStatus()
    },
    assertions = {
      verify(localDataSource).getMarketStatus()
      verifyNoMoreInteractions(localDataSource)
      verifyZeroInteractions(remoteDataSource)

      Assertions.assertThat(it).isEqualTo(expectedMarket)
    }
  )

  @Test
  fun `should return all by code`() = testScenario(
    scenario = {
      whenever(localDataSource.getAllByCode(any())).thenReturn(expectedAll)
    },
    action = {
      repository.getAllByCode("MGLU3")
    },
    assertions = {
      verify(localDataSource).getAllByCode("MGLU3")
      verifyNoMoreInteractions(localDataSource)
      verifyZeroInteractions(remoteDataSource)

      Assertions.assertThat(it).isEqualTo(expectedAll)
    }
  )

  @Test
  fun `should edit stock price`() = testScenario(
    action = {
      repository.editStockShareValue("", 10.0)
    },
    assertions = {
      verify(localDataSource).editStockShareValue("", 10.0)
      verifyNoMoreInteractions(localDataSource)
      verifyZeroInteractions(remoteDataSource)
    }
  )

  @Test
  fun `should not sync prices`() = testScenario(
    scenario = {
      whenever(localDataSource.getDistinctStockCode()).thenReturn(emptyList())
    },
    action = {
      repository.syncStockSharePrice()
    },
    assertions = {
      verify(localDataSource).getDistinctStockCode()
      verifyNoMoreInteractions(localDataSource)
      verifyZeroInteractions(remoteDataSource)
    }
  )

  @Test
  fun `should sync prices with empty response`() = testScenario(
    scenario = {
      whenever(localDataSource.getDistinctStockCode()).thenReturn(
        listOf(
          "MGLU3", "WEGE3", "SQIA3"
        )
      )

      whenever(remoteDataSource.getCurrentStockSharePrice(any())).thenReturn(
        StockPriceResponse()
      )
    },
    action = {
      repository.syncStockSharePrice()
    },
    assertions = {
      verify(localDataSource).getDistinctStockCode()
      verify(remoteDataSource).getCurrentStockSharePrice("MGLU3")
      verify(remoteDataSource).getCurrentStockSharePrice("WEGE3")
      verify(remoteDataSource).getCurrentStockSharePrice("SQIA3")
      verifyZeroInteractions(logger)
      verifyNoMoreInteractions(localDataSource)
      verifyNoMoreInteractions(remoteDataSource)
    }
  )

  @Test
  fun `should log error`() = testScenario(
    scenario = {
      whenever(localDataSource.getDistinctStockCode()).thenReturn(
        listOf(
          "MGLU3", "WEGE3", "SQIA3"
        )
      )

      whenever(remoteDataSource.getCurrentStockSharePrice(any())).thenThrow(
        expectedError
      )
    },
    action = {
      repository.syncStockSharePrice()
    },
    assertions = {
      verify(localDataSource).getDistinctStockCode()
      verifyNoMoreInteractions(localDataSource)
      verify(logger, times(3)).e(e = expectedError)
    }
  )

  @Test
  fun `should sync prices`() = testScenario(
    scenario = {
      whenever(localDataSource.getDistinctStockCode()).thenReturn(
        listOf(
          "MGLU3", "WEGE3", "SQIA3"
        )
      )

      whenever(remoteDataSource.getCurrentStockSharePrice(any())).thenReturn(
        StockPriceResponse(
          price = 10.0,
          marketChange = 1.1,
          previousClose = 2.0
        )
      )
    },
    action = {
      repository.syncStockSharePrice()
    },
    assertions = {
      verifyZeroInteractions(logger)
      verify(localDataSource).editStockShareValue("MGLU3", 10.0, 1.1, 2.0)
      verify(localDataSource).editStockShareValue("WEGE3", 10.0, 1.1, 2.0)
      verify(localDataSource).editStockShareValue("SQIA3", 10.0, 1.1, 2.0)
    }
  )
}