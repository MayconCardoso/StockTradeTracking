package com.mctech.stocktradetracking.data.timeline_balance.datasource

import com.mctech.stocktradetracking.data.timeline_balance.database.TimelineBalanceDao
import com.mctech.stocktradetracking.data.timeline_balance.mapper.toDatabaseEntity
import com.mctech.stocktradetracking.testing.data_factory.factories.TimelineBalanceFactory
import com.mctech.stocktradetracking.testing.data_factory.testScenario
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LocalTimelineBalanceDataSourceTest {
    private val dao = mock<TimelineBalanceDao>()
    private val expectedSingle = TimelineBalanceFactory.single()
    private val expectedList = TimelineBalanceFactory.listOf(10)

    private lateinit var dataSource: LocalTimelineBalanceDataSource

    @Before
    fun `before each test`() {
        dataSource = LocalTimelineBalanceDataSource(dao)
    }

    @Test
    fun `should load null last period`() = testScenario(
        scenario = {
            whenever(dao.loadLastPeriod()).thenReturn(null)
        },
        action = {
            dataSource.getLastPeriod()
        },
        assertions = {
            Assertions.assertThat(it).isNull()
            verify(dao).loadLastPeriod()
            verifyNoMoreInteractions(dao)
        }
    )

    @Test
    fun `should load last period`() = testScenario(
        scenario = {
            whenever(dao.loadLastPeriod()).thenReturn(expectedSingle.toDatabaseEntity())
        },
        action = {
            dataSource.getLastPeriod()
        },
        assertions = {
            Assertions.assertThat(it).isNotNull
            verify(dao).loadLastPeriod()
            verifyNoMoreInteractions(dao)
        }
    )

    @Test
    fun `should load list of period`() = testScenario(
        scenario = {
            whenever(dao.loadListOfPeriodsBalance()).thenReturn(expectedList.map { it.toDatabaseEntity() })
        },
        action = {
            dataSource.getListOfPeriodsBalance()
        },
        assertions = {
            Assertions.assertThat(it).isNotNull
            Assertions.assertThat(it.size).isEqualTo(10)
            verify(dao).loadListOfPeriodsBalance()
            verifyNoMoreInteractions(dao)
        }
    )

    @Test
    fun `should create period`() = testScenario(
        action = {
            dataSource.createPeriod(expectedSingle)
        },
        assertions = {
            verify(dao).save(any())
            verifyNoMoreInteractions(dao)
        }
    )

    @Test
    fun `should edit period`() = testScenario(
        action = {
            dataSource.editPeriod(expectedSingle)
        },
        assertions = {
            verify(dao).save(any())
            verifyNoMoreInteractions(dao)
        }
    )
}