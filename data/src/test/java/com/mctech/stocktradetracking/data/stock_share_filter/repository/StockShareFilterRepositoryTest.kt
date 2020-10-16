package com.mctech.stocktradetracking.data.stock_share_filter.repository

import com.mctech.stocktradetracking.data.BaseCoroutineTest
import com.mctech.stocktradetracking.data.stock_share_filter.datasource.LocalStockShareFilterDataSource
import com.mctech.stocktradetracking.testing.data_factory.factories.StockShareFilterDataFactory
import com.mctech.stocktradetracking.testing.data_factory.testMockedFlowScenario
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
class StockShareFilterRepositoryTest : BaseCoroutineTest() {
  private val dataSource = mock<LocalStockShareFilterDataSource>()
  private val expectedSingle = StockShareFilterDataFactory.single()

  private lateinit var repository: StockShareFilterRepository

  @Before
  fun `before each test`() {
    repository = StockShareFilterRepository(dataSource)
  }

  @Test
  fun `should save filter`() = testScenario(
    action = {
      repository.saveFilter(expectedSingle)
    },
    assertions = {
      verify(dataSource).saveFilter(expectedSingle)
      verifyNoMoreInteractions(dataSource)
    }
  )

  @Test
  fun `should delegate flow`() = testScenario(
    action = {
      repository.observeStockShareFilter()
    },
    assertions = {
      verify(dataSource).observeStockShareFilter()
      verifyNoMoreInteractions(dataSource)
    }
  )

  @Test
  fun `should return the same flow`() = testMockedFlowScenario(
    observe = {
      repository.observeStockShareFilter()
    },
    scenario = { mockedFlow ->
      whenever(dataSource.observeStockShareFilter()).thenReturn(mockedFlow)
    },
    action = { emitter ->
      emitter.emit(expectedSingle)
    },
    assertions = {
      Assertions.assertThat(it.size).isEqualTo(1)
      Assertions.assertThat(it.first()).isEqualTo(expectedSingle)
    }
  )
}