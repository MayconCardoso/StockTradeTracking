package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.stocktradetracking.testing.data_factory.factories.StockShareDataFactory
import com.mctech.stocktradetracking.testing.data_factory.testScenario
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SelectWorstDailyStockShareCaseTest{
    private lateinit var useCase: SelectWorstDailyStockShareCase

    @Before
    fun `before each test`() {
        useCase = SelectWorstDailyStockShareCase(
            groupStockShareListCase = GroupStockShareListCase()
        )
    }

    @Test
    fun `should group list by code`() = testScenario(
        action = {
            useCase.execute(StockShareDataFactory.ungroupedList())
        },
        assertions =  { wege3 ->
            Assertions.assertThat(wege3).isNotNull
            Assertions.assertThat(wege3?.code).isEqualTo("WEGE3")
            Assertions.assertThat(wege3?.balanceDescription).isEqualTo("-R$4.800,00")
            Assertions.assertThat(wege3?.variation).isEqualTo(-80.00)
            Assertions.assertThat(wege3?.variationDescription).isEqualTo("-80.0%")
        }
    )
}