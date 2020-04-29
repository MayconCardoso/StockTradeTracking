package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.stocktradetracking.testing.data_factory.factories.StockShareDataFactory
import com.mctech.stocktradetracking.testing.data_factory.testScenario
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetBestStockShareCaseTest{
    private lateinit var useCase: GetBestStockShareCase

    @Before
    fun `before each test`() {
        useCase = GetBestStockShareCase(
            groupStockShareListCase = GroupStockShareListCase()
        )
    }

    @Test
    fun `should group list by code`() = testScenario(
        action = {
            useCase.execute(StockShareDataFactory.ungroupedList())
        },
        assertions = { mglu ->
            assertThat(mglu.getInvestmentValue()).isEqualTo(5200.0)
            assertThat(mglu.shareAmount).isEqualTo(700)
            assertThat(mglu.purchasePrice).isEqualTo(7.428571428571429)
            assertThat(mglu.salePrice).isEqualTo(10.0)
        }
    )
}