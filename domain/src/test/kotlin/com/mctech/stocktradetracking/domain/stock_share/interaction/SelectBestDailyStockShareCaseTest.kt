package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.stocktradetracking.testing.data_factory.factories.StockShareDataFactory
import com.mctech.stocktradetracking.testing.data_factory.testScenario
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class SelectBestDailyStockShareCaseTest {
  private lateinit var useCase: SelectBestDailyStockShareCase

  @Before
  fun `before each test`() {
    useCase = SelectBestDailyStockShareCase(
      groupStockShareListCase = GroupStockShareListCase()
    )
  }

  @Test
  fun `should group list by code`() = testScenario(
    action = {
      useCase.execute(StockShareDataFactory.ungroupedList())
    },
    assertions = { mglu ->
      Assertions.assertThat(mglu).isNotNull
      Assertions.assertThat(mglu?.code).isEqualTo("MGLU3")
      Assertions.assertThat(mglu?.balanceDescription).isEqualTo("R$1.400,00")
      Assertions.assertThat(mglu?.variation).isEqualTo(1400.0)
      Assertions.assertThat(mglu?.variationDescription).isEqualTo("25.0%")
    }
  )
}