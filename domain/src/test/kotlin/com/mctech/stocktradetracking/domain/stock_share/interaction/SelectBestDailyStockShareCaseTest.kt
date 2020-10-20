package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.stocktradetracking.domain.stock_share_filter.entity.RankingQualifier
import com.mctech.stocktradetracking.testing.data_factory.factories.StockShareDataFactory
import com.mctech.stocktradetracking.testing.data_factory.factories.StockShareFilterDataFactory
import com.mctech.stocktradetracking.testing.data_factory.testScenario
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SelectBestDailyStockShareCaseTest {
  private lateinit var useCase: SelectBestDailyStockShareCase
  private val balanceFilter = StockShareFilterDataFactory.single(ranking = RankingQualifier.Balance)
  private val percentFilter = StockShareFilterDataFactory.single(ranking = RankingQualifier.Percent)

  @Before
  fun `before each test`() {
    useCase = SelectBestDailyStockShareCase(
      groupStockShareListCase = GroupStockShareListCase()
    )
  }

  @Test
  fun `should return best daily stock by balance`() = testScenario(
    action = {
      useCase.execute(StockShareDataFactory.ungroupedList(), balanceFilter)
    },
    assertions = { mglu ->
      Assertions.assertThat(mglu).isNotNull
      Assertions.assertThat(mglu?.code).isEqualTo("MGLU3")
      Assertions.assertThat(mglu?.balanceDescription).isEqualTo("R$1.400,00")
      Assertions.assertThat(mglu?.variation).isEqualTo(1400.0)
      Assertions.assertThat(mglu?.variationDescription).isEqualTo("25.0%")
    }
  )

  @Test
  fun `should return best daily stock by percent`() = testScenario(
    action = {
      useCase.execute(StockShareDataFactory.ungroupedList(), percentFilter)
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