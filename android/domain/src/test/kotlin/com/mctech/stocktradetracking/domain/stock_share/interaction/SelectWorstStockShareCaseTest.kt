package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.stocktradetracking.testing.data_factory.factories.StockShareDataFactory
import com.mctech.stocktradetracking.testing.data_factory.testScenario
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SelectWorstStockShareCaseTest{
    private lateinit var useCase: SelectWorstStockShareCase

    @Before
    fun `before each test`() {
        useCase = SelectWorstStockShareCase(
            groupStockShareListCase = GroupStockShareListCase()
        )
    }

    @Test
    fun `should group list by code`() = testScenario(
        action = {
            useCase.execute(StockShareDataFactory.ungroupedList())
        },
        assertions =  { wege3 ->
            assertThat(wege3).isNotNull
            assertThat(wege3?.code).isEqualTo("WEGE3")
            assertThat(wege3?.balanceDescription).isEqualTo("-R$1.000,00")
            assertThat(wege3?.variation).isEqualTo(-45.45)
            assertThat(wege3?.variationDescription).isEqualTo("-45.45%")
        }
    )
}