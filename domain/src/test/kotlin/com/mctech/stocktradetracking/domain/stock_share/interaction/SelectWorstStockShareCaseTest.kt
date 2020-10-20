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
class SelectWorstStockShareCaseTest {
  private lateinit var useCase: SelectWorstStockShareCase
  private val balanceFilter = StockShareFilterDataFactory.single(ranking = RankingQualifier.Balance)
  private val percentFilter = StockShareFilterDataFactory.single(ranking = RankingQualifier.Percent)

  @Before
  fun `before each test`() {
    useCase = SelectWorstStockShareCase(
      groupStockShareListCase = GroupStockShareListCase()
    )
  }

  @Test
  fun `should return worst stock by balance`() = testScenario(
    action = {
      useCase.execute(StockShareDataFactory.ungroupedList(), balanceFilter)
    },
    assertions = { wege3 ->
      assertThat(wege3).isNotNull
      assertThat(wege3?.code).isEqualTo("WEGE3")
      assertThat(wege3?.balanceDescription).isEqualTo("-R$1.000,00")
      assertThat(wege3?.variation).isEqualTo(-45.45)
      assertThat(wege3?.variationDescription).isEqualTo("-45.45%")
    }
  )

  @Test
  fun `should return worst stock by percent`() = testScenario(
    action = {
      useCase.execute(StockShareDataFactory.ungroupedList(), percentFilter)
    },
    assertions = { wege3 ->
      assertThat(wege3).isNotNull
      assertThat(wege3?.code).isEqualTo("WEGE3")
      assertThat(wege3?.balanceDescription).isEqualTo("-R$1.000,00")
      assertThat(wege3?.variation).isEqualTo(-45.45)
      assertThat(wege3?.variationDescription).isEqualTo("-45.45%")
    }
  )
}