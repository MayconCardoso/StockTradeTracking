package com.mctech.stocktradetracking.data.timeline_balance.repository

import com.mctech.stocktradetracking.data.timeline_balance.datasource.TimelineBalanceDataSource
import com.mctech.stocktradetracking.testing.data_factory.factories.TimelineBalanceFactory
import com.mctech.stocktradetracking.testing.data_factory.testScenario
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class TimelineBalanceRepositoryTest {
  private val dataSource = mock<TimelineBalanceDataSource>()
  private val expectedSingle = TimelineBalanceFactory.single()
  private val expectedList = TimelineBalanceFactory.listOf(10)

  private lateinit var repository: TimelineBalanceRepository

  @Before
  fun `before each test`() {
    repository = TimelineBalanceRepository(dataSource)
  }

  @Test
  fun `should load null last period`() = testScenario(
    scenario = {
      whenever(dataSource.getLastPeriod()).thenReturn(null)
    },
    action = {
      repository.getLastPeriod()
    },
    assertions = {
      Assertions.assertThat(it).isNull()
      verify(dataSource).getLastPeriod()
      verifyNoMoreInteractions(dataSource)
    }
  )

  @Test
  fun `should load last period`() = testScenario(
    scenario = {
      whenever(dataSource.getLastPeriod()).thenReturn(expectedSingle)
    },
    action = {
      repository.getLastPeriod()
    },
    assertions = {
      Assertions.assertThat(it).isNotNull
      verify(dataSource).getLastPeriod()
      verifyNoMoreInteractions(dataSource)
    }
  )

  @Test
  fun `should load list of period`() = testScenario(
    scenario = {
      whenever(dataSource.getListOfPeriodsBalance()).thenReturn(expectedList)
    },
    action = {
      repository.getListOfPeriodsBalance()
    },
    assertions = {
      Assertions.assertThat(it).isNotNull
      Assertions.assertThat(it.size).isEqualTo(10)
      verify(dataSource).getListOfPeriodsBalance()
      verifyNoMoreInteractions(dataSource)
    }
  )

  @Test
  fun `should create period`() = testScenario(
    action = {
      repository.createPeriod(expectedSingle)
    },
    assertions = {
      verify(dataSource).createPeriod(any())
      verifyNoMoreInteractions(dataSource)
    }
  )

  @Test
  fun `should edit period`() = testScenario(
    action = {
      repository.editPeriod(expectedSingle)
    },
    assertions = {
      verify(dataSource).editPeriod(any())
      verifyNoMoreInteractions(dataSource)
    }
  )
}