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
class SelectWorstDailyStockShareCaseTest {
  private lateinit var useCase: SelectWorstDailyStockShareCase
  private val balanceFilter = StockShareFilterDataFactory.single(ranking = RankingQualifier.Balance)
  private val percentFilter = StockShareFilterDataFactory.single(ranking = RankingQualifier.Percent)

  @Before
  fun `before each test`() {
    useCase = SelectWorstDailyStockShareCase(
      groupStockShareListCase = GroupStockShareListCase()
    )
  }

  @Test
  fun `should return worst daily stock by balance`() = testScenario(
    action = {
      useCase.execute(StockShareDataFactory.ungroupedList(), balanceFilter)
    },
    assertions = { wege3 ->
      Assertions.assertThat(wege3).isNotNull
      Assertions.assertThat(wege3?.code).isEqualTo("WEGE3")
      Assertions.assertThat(wege3?.balanceDescription).isEqualTo("-R$4.800,00")
      Assertions.assertThat(wege3?.variation).isEqualTo(-80.00)
      Assertions.assertThat(wege3?.variationDescription).isEqualTo("-80.0%")
    }
  )

  @Test
  fun `should return worst daily stock by percent`() = testScenario(
    action = {
      useCase.execute(StockShareDataFactory.ungroupedList(), percentFilter)
    },
    assertions = { wege3 ->
      Assertions.assertThat(wege3).isNotNull
      Assertions.assertThat(wege3?.code).isEqualTo("WEGE3")
      Assertions.assertThat(wege3?.balanceDescription).isEqualTo("-R$4.800,00")
      Assertions.assertThat(wege3?.variation).isEqualTo(-80.00)
      Assertions.assertThat(wege3?.variationDescription).isEqualTo("-80.0%")
    }
  )
}