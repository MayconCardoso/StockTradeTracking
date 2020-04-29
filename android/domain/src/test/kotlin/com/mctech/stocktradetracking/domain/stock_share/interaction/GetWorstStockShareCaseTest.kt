package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.stocktradetracking.testing.data_factory.factories.StockShareDataFactory
import com.mctech.stocktradetracking.testing.data_factory.testScenario
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

/**
 * @author MAYCON CARDOSO on 28/04/20.
 */
@ExperimentalCoroutinesApi
class GetWorstStockShareCaseTest{
    private lateinit var useCase: GetWorstStockShareCase

    @Before
    fun `before each test`() {
        useCase = GetWorstStockShareCase(
            groupStockShareListCase = GroupStockShareListCase()
        )
    }

    @Test
    fun `should group list by code`() = testScenario(
        action = {
            useCase.execute(StockShareDataFactory.ungroupedList())
        },
        assertions = { weg3 ->
            assertThat(weg3?.getInvestmentValue()).isEqualTo(2200.0)
            assertThat(weg3?.shareAmount).isEqualTo(600)
            assertThat(weg3?.purchasePrice).isEqualTo(3.6666666666666665)
            assertThat(weg3?.salePrice).isEqualTo(2.0)
        }
    )
}