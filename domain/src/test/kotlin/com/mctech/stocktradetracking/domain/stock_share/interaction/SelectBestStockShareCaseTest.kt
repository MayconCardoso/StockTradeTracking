package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.stocktradetracking.testing.data_factory.factories.StockShareDataFactory
import com.mctech.stocktradetracking.testing.data_factory.testScenario
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SelectBestStockShareCaseTest {
    private lateinit var useCase: SelectBestStockShareCase

    @Before
    fun `before each test`() {
        useCase = SelectBestStockShareCase(
            groupStockShareListCase = GroupStockShareListCase()
        )
    }

    @Test
    fun `should group list by code`() = testScenario(
        action = {
            useCase.execute(StockShareDataFactory.ungroupedList())
        },
        assertions = { mglu ->
            assertThat(mglu).isNotNull
            assertThat(mglu?.code).isEqualTo("MGLU3")
            assertThat(mglu?.balanceDescription).isEqualTo("R$1.800,00")
            assertThat(mglu?.variation).isEqualTo(34.62)
            assertThat(mglu?.variationDescription).isEqualTo("34.62%")
        }
    )
}