package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.stocktradetracking.domain.stock_share_filter.entity.RankingQualifier
import com.mctech.stocktradetracking.testing.data_factory.factories.StockShareDataFactory
import com.mctech.stocktradetracking.testing.data_factory.factories.StockShareFilterDataFactory
import com.mctech.stocktradetracking.testing.data_factory.testScenario
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SelectBestStockShareCaseTest {
  private lateinit var useCase: SelectBestStockShareCase
  private val balanceFilter = StockShareFilterDataFactory.single(ranking = RankingQualifier.Balance)
  private val percentFilter = StockShareFilterDataFactory.single(ranking = RankingQualifier.Percent)

  @Before
  fun `before each test`() {
    useCase = SelectBestStockShareCase(
      groupStockShareListCase = GroupStockShareListCase()
    )
  }

  @Test
  fun `should return best stock by balance`() = testScenario(
    action = {
      useCase.execute(StockShareDataFactory.ungroupedList(), balanceFilter)
    },
    assertions = { mglu ->
      assertThat(mglu).isNotNull
      assertThat(mglu?.code).isEqualTo("MGLU3")
      assertThat(mglu?.balanceDescription).isEqualTo("R$1.800,00")
      assertThat(mglu?.variation).isEqualTo(34.62)
      assertThat(mglu?.variationDescription).isEqualTo("34.62%")
    }
  )

  @Test
  fun `should return best stock by percent`() = testScenario(
    action = {
      useCase.execute(StockShareDataFactory.ungroupedList(), percentFilter)
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