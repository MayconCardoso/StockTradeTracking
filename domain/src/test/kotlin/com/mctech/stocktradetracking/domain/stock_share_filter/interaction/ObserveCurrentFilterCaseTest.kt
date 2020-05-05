package com.mctech.stocktradetracking.domain.stock_share_filter.interaction

import com.mctech.stocktradetracking.domain.CoroutinesMainTestRule
import com.mctech.stocktradetracking.domain.stock_share_filter.entity.FilterSort
import com.mctech.stocktradetracking.domain.stock_share_filter.entity.RankingQualifier
import com.mctech.stocktradetracking.domain.stock_share_filter.service.StockShareFilterService
import com.mctech.stocktradetracking.testing.data_factory.factories.StockShareFilterDataFactory
import com.mctech.stocktradetracking.testing.data_factory.testMockedFlowScenario
import com.mctech.stocktradetracking.testing.data_factory.testScenario
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ObserveCurrentFilterCaseTest {
    @get:Rule
    val coroutinesTestRule = CoroutinesMainTestRule()

    private val service         = mock<StockShareFilterService>()
    private lateinit var useCase: ObserveCurrentFilterCase

    private val savedFlow = StockShareFilterDataFactory.single(
        false,
        FilterSort.NameAsc,
        RankingQualifier.Percent
    )

    @Before
    fun `before each test`() {
        useCase = ObserveCurrentFilterCase(service)
    }

    @Test
    fun `should delegate calling`() = testScenario(
        action = {
            useCase.execute()
        },
        assertions = {
            verify(service).observeStockShareFilter()
            verifyNoMoreInteractions(service)
        }
    )

    @Test
    fun `should return current filter`() = testMockedFlowScenario(
        observe = {
            useCase.execute()
        },
        scenario = { mockedFlow ->
            whenever(service.observeStockShareFilter()).thenReturn(mockedFlow)
        },
        action = { emitter ->
            emitter.emit(savedFlow)
        },
        assertions = {
            assertThat(it.size).isEqualTo(1)
            assertThat(it.first().isGroupingStock).isFalse()
            assertThat(it.first().sort).isEqualTo(FilterSort.NameAsc)
            assertThat(it.first().rankingQualifier).isEqualTo(RankingQualifier.Percent)
        }
    )

    @Test
    fun `should return default filter`() = testMockedFlowScenario(
        observe = {
            useCase.execute()
        },
        scenario = { mockedFlow ->
            whenever(service.observeStockShareFilter()).thenReturn(mockedFlow)
        },
        action = { emitter ->
            emitter.emit(null)
        },
        assertions = {
            assertThat(it.size).isEqualTo(1)
            assertThat(it.first().isGroupingStock).isTrue()
            assertThat(it.first().sort).isEqualTo(FilterSort.BalanceDesc)
            assertThat(it.first().rankingQualifier).isEqualTo(RankingQualifier.Balance)
        }
    )
}